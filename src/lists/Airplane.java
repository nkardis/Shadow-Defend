
package lists;

import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.util.Point;

import java.util.List;


public class Airplane extends Tower {

    private static final String IMAGE_FILE = "res/images/airsupport.png";
    private static final String PROJ_FILE = "res/images/explosive.png";
    private static final double SPEED = 5;
    private static final double RADIUS = 200;
    private static final double DAMAGE = 500;
    private static final double DETONATION_TIME = 2;
    private static final double COST = 500;
    private final Point line;
    private boolean shooting;
    private final Image explosive;

    public static double getCost() {return COST;}

    public Airplane(Point line, String filename) {
        super(line, IMAGE_FILE);
        this.line = line;
        this.explosive = new Image(PROJ_FILE);
        this.shooting = false;
    }

    @Override
    public void update(Input input) {
        explosive.draw(getCenter().x, getCenter().y, new DrawOptions().setRotation(0));
    }

    public boolean isFinished() {
        return false;
    }
}


