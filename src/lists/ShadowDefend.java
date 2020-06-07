package lists;

import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * ShadowDefend, a tower defence game.
 */
public class ShadowDefend extends AbstractGame {

    private static final int HEIGHT = 768;
    private static final int WIDTH = 1024;
    // Change to suit system specifications. This could be
    // dynamically determined but that is out of scope.
    public static final double FPS = 144;
    // The spawn delay (in seconds) to spawn slicers
    private static final int INTIAL_TIMESCALE = 1;
    private static int waveNumber;
    private int maxSlicers;
    private double spawnDelay;
    private static int WAVE_POSITION = 0;
    // Timescale is made static because it is a universal property of the game and the specification
    // says everything in the game is affected by this
    private static int timescale = INTIAL_TIMESCALE;
    private static int wavetxtIter = 1;
    private final TiledMap map;
    private final List<Point> polyline;
    private final List<Slicer> slicers;
    private double frameCount;
    private int spawnedSlicers;
    private static boolean waveStarted;
    private boolean mapCompleted = false;
    private final ArrayList<String> WAVES;
    private final Panel panel = new Panel();

    /**
     * Creates a new instance of the ShadowDefend game
     */
    public ShadowDefend() {
        super(WIDTH, HEIGHT, "ShadowDefend");
        this.map = new TiledMap(LoadLevel.loadMap(mapCompleted));
        this.polyline = map.getAllPolylines().get(0);
        this.slicers = new ArrayList<>();
        this.spawnedSlicers = 0;
        waveStarted = false;
        this.frameCount = Integer.MAX_VALUE;
        // Temporary fix for the weird slicer map glitch (might have to do with caching textures)
        // This fix is entirely optional
        new Slicer(polyline);
        this.WAVES = LoadLevel.makeFormat();
    }

    /**
     * The entry-point for the game
     *
     * @param args Optional command-line arguments
     */
    public static void main(String[] args) {
        new ShadowDefend().run();
    }

    public static int getWave(String[] waveData) {return Integer.parseInt(waveData[0]);}

    private double getSpawnDelay(String[] waveData) {
        return Integer.parseInt(waveData[4])/1000;
    }

    public static int getTimescale() {return timescale; }

    public static boolean isWaveStarted() {return waveStarted; }

    public static int getWaveNumber() {return waveNumber;}

    private static int getWaveIter(){
        return wavetxtIter;
    }

    /**
     * Increases the timescale
     */
    private void increaseTimescale() {
        if(timescale < 5) {timescale++;}; }

    /**
     * Decreases the timescale but doesn't go below the base timescale
     */
    private void decreaseTimescale() {
        if (timescale > INTIAL_TIMESCALE) { timescale--; } }

    /**
     * Parses the next wave to be run by the game
     * @param wave wave number of round
     * @return returns wave properties <wave number>,spawn,<number to spawn>,<enemy type>,<spawn delay in milliseconds>
     */
    private String[] nextWave(int wave) {
        return WAVES.get(wave - 1).split(",");
    }

    private boolean ifSpawnWave(String[] waveData) {
        return waveData[1].equals("spawn");
    }

    private void increaseWaveIter(){
        wavetxtIter++;
    }

    /**
     * Update the state of the game, potentially reading from input
     *
     * @param input The current mouse/keyboard state
     */
    @Override
    protected void update(Input input) {
        // Increase the frame counter by the current timescale
        frameCount += getTimescale();
        String[] waveData = nextWave(getWaveIter());
        if (ifSpawnWave(waveData)) {
            maxSlicers = Integer.parseInt(waveData[2]);
        }
        waveNumber = getWave(waveData);
        spawnDelay = getSpawnDelay(waveData);
        // Draw map from the top left of the window
        map.draw(0, 0, 0, 0, WIDTH, HEIGHT);
        panel.update(input);


        // Handle key presses
        if (input.wasPressed(Keys.S)) {
            waveStarted = true;
        }

        if (input.wasPressed(Keys.L)) {
            increaseTimescale();
        }

        if (input.wasPressed(Keys.K)) {
            decreaseTimescale();
        }

        // Check if it is time to spawn a new slicer (and we have some left to spawn)
        // To Do: Add logic to do different slicer spawns
        if (waveStarted && frameCount / FPS >= spawnDelay && spawnedSlicers != maxSlicers) {
            slicers.add(new Slicer(polyline));
            spawnedSlicers += 1;
            // Reset frame counter
            frameCount = 0;
        }

        // Close game if all slicers have finished traversing the polyline
        if (spawnedSlicers == maxSlicers && slicers.isEmpty()) {
            Window.close();
        }

        // Update all sprites, and remove them if they've finished
        for (int i = slicers.size() - 1; i >= 0; i--) {
            Slicer s = slicers.get(i);
            s.update(input);
            if (s.isFinished()) {
                slicers.remove(i);
                panel.decreaseLives();
            }
        }
    }

}
