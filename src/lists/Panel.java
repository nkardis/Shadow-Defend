package lists;

import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Panel {

    private static final String BUY_PANEL = "res/images/buypanel.png";
    private static final String STATUS_PANEL = "res/images/statuspanel.png";
    private static final String FONT = "res/fonts/DejaVuSans-Bold.ttf";
    private static final String TANK = "res/images/tank.png";
    private static final String SUPERTANK = "res/images/supertank.png";
    private static final String AIRSUPPORT = "res/images/airsupport.png";
    private static final String BINDS = "Key binds \n\nS - Start Wave\nL - Increase Timescale\nK - Decrease Timescale";
    private static final String INPROGRESS = "Status: Wave in Progress";
    private static final String WIN = "Status: Winner";
    private static final String PLACE = "Status: Placing";
    private static final String WAITING = "Status: Awaiting Start";
    private static final double TANKPRICE = 250;
    private static final double SUPERTANKPRICE = 600;
    private static final double AIRSUPPORTPRICE = 500;
    private final Image topPanel;
    private final Image botPanel;
    private final Image tank;
    private final Image superTank;
    private final Image airSupport;
    private final Font moneyFont;
    private final Font priceFont;
    private final Font bindFont;
    private final Font statusPanelFont;
    private static final int LEFT_SPACING = 64;
    private static final int GAP_SPACING = 120;
    private static final int PIXEL_CENTRE = 10;
    private static final int RIGHT_SPACING = 200;
    private static final int TOP_SPACING = 65;
    private int lives = 25;
    private double money = 500;

    private final Point tankPos;
    private final Point superTankPos;
    private final Point airSupportPos;

    private boolean placingTank = false;
    private boolean placingSuperTank = false;
    private boolean placingAirSupport = false;

    private final List<Tower> towers;

    public Panel() {
        this.topPanel = new Image(BUY_PANEL);
        this.botPanel = new Image(STATUS_PANEL);
        this.tank = new Image(TANK);
        this.superTank = new Image(SUPERTANK);
        this.airSupport = new Image(AIRSUPPORT);
        this.moneyFont = new Font(FONT, 48);
        this.priceFont = new Font(FONT, 24);
        this.bindFont = new Font(FONT, 14);
        this.statusPanelFont = new Font(FONT, 18);
        this.tankPos = new Point(LEFT_SPACING, getItemVerticalOffset());
        this.superTankPos = new Point(LEFT_SPACING + GAP_SPACING, getItemVerticalOffset());
        this.airSupportPos = new Point(LEFT_SPACING + 2*GAP_SPACING, getItemVerticalOffset());

        this.towers = new ArrayList<>();
    }

    private double getStatusPanelLocation() {
        return Window.getHeight() - botPanel.getHeight();
    }

    private double getItemVerticalOffset() {
        return topPanel.getHeight()/2 - PIXEL_CENTRE;
    }

    private double getPriceVerticalOffset() {
        return topPanel.getHeight() - PIXEL_CENTRE;
    }

    private double getMoneyOffset() {
        return topPanel.getWidth() - RIGHT_SPACING;
    }

    private double statusPanelCentering() {
        return getStatusPanelLocation() + botPanel.getHeight() - 7;
    }

    private boolean isPlacing() {return placingTank || placingSuperTank || placingAirSupport;}

    private Colour towerPurchaseable(double towerPrice) {
        if(money >= towerPrice) {
            return Colour.GREEN;
        } else { return Colour.RED;}
    }

    private Colour timeScaleColour(double timescale) {
        if (timescale > 1) {
            return Colour.GREEN;
        } else { return Colour.WHITE; }
    }
    /* Implement Placing/Winner when drag/drop is done */
    private String waveStatus() {
        if (ShadowDefend.isWaveStarted()) {
            return INPROGRESS;
        } else if (isPlacing()) {
            return PLACE;
        } else {
            return WAITING;
        }
    }

    public int getLives() {
            return lives;
        }

    /**
     * Subtracts amount of lives when a slicer finishes on polyline
      * @param slicerType takes the class of the slicer finished removes amount of lives associated
     */
    public void decreaseLives(Class<? extends Sprite> slicerType) {
        if (slicerType.equals(Slicer.class))
            {lives--;}
        else if (slicerType.equals(SuperSlicer.class)){
            for (int i = 0; i < SuperSlicer.getLifeLoss(); i++){lives--;}
        }
        else if (slicerType.equals(MegaSlicer.class)){
            for (int i = 0; i < MegaSlicer.getLifeLoss(); i++){lives--;}
        }
        else if (slicerType.equals(ApexSlicer.class)){
            for (int i = 0; i < ApexSlicer.getLifeLoss(); i++){lives--;}
        }
    }

    private void drawBuyPanel(){
        /* Top panel draw*/
        topPanel.drawFromTopLeft(0,0);
        // Tank draw and pricing
        tank.draw(tankPos.x, tankPos.y);
        priceFont.drawString(String.valueOf("$" + TANKPRICE),LEFT_SPACING/2, getPriceVerticalOffset(),
                new DrawOptions().setBlendColour(towerPurchaseable(TANKPRICE)));
        // Super tank draw and pricing underneath
        superTank.draw(superTankPos.x, superTankPos.y);
        priceFont.drawString(String.valueOf("$" + SUPERTANKPRICE), LEFT_SPACING/2 + GAP_SPACING, getPriceVerticalOffset(),
                new DrawOptions().setBlendColour(towerPurchaseable(SUPERTANKPRICE)));
        // Air support draw and pricing underneath
        airSupport.draw(airSupportPos.x, airSupportPos.y);
        priceFont.drawString(String.valueOf("$" + AIRSUPPORTPRICE), LEFT_SPACING/2 + 2*GAP_SPACING, getPriceVerticalOffset(),
                new DrawOptions().setBlendColour(towerPurchaseable(AIRSUPPORTPRICE)));
        // Money draw
        moneyFont.drawString(String.valueOf(money), getMoneyOffset(), TOP_SPACING);
        // Binding draw
        bindFont.drawString(BINDS, topPanel.getWidth()/2 - bindFont.getWidth(BINDS)/2, getItemVerticalOffset());
    }

    private void drawStatusPanel(){
        /* Status panel draw */
        botPanel.drawFromTopLeft(0, getStatusPanelLocation());
        // Wave counter draw
        statusPanelFont.drawString(String.valueOf("Wave: " + ShadowDefend.getWaveNumber()),5,
                statusPanelCentering());
        // Timescale draw
        statusPanelFont.drawString(String.valueOf("Time Scale: " + ShadowDefend.getTimescale()), 5 + 2*GAP_SPACING,
                statusPanelCentering(), new DrawOptions().setBlendColour(timeScaleColour(ShadowDefend.getTimescale())));
        // Wave Status draw
        statusPanelFont.drawString(waveStatus(),
                botPanel.getWidth()/2 - statusPanelFont.getWidth(waveStatus())/2,statusPanelCentering());
        // Life meter draw
        statusPanelFont.drawString("Lives: " + getLives(),
                botPanel.getWidth() - (statusPanelFont.getWidth("Lives: " + getLives()) + 5), statusPanelCentering());
    }

    public void update(Input input) {
    drawBuyPanel();
    drawStatusPanel();
    // Creates Point that contains position of mouse updated each cycle
    Point mousePos = input.getMousePosition();

    // When hovered and clicked, an Image of the tower is drawn where mouse position is
    if (tank.getBoundingBoxAt(tankPos).intersects(mousePos) && input.isDown(MouseButtons.LEFT)
            && !placingSuperTank && !placingAirSupport){
        placingTank = true;
    } else if (superTank.getBoundingBoxAt(superTankPos).intersects(mousePos) && input.isDown(MouseButtons.LEFT )
            && !placingTank && !placingAirSupport){
        placingSuperTank = true;
    }
    // Player cant hover multiple towers are picked up for placement at the same time
    // and doesn't allow for tower to be placed on the Panels and are placed in playable area
    if (placingTank && !placingSuperTank && !placingAirSupport){
        tank.draw(mousePos.x, mousePos.y);
        if (input.wasReleased(MouseButtons.LEFT) && !topPanel.getBoundingBox().intersects(mousePos)
                && !botPanel.getBoundingBox().intersects(mousePos)) {
            towers.add(new Tank(mousePos, TANK));
            placingTank = false;
        }
    } else if (placingSuperTank && !placingTank && !placingAirSupport){
        superTank.draw(mousePos.x, mousePos.y);
        if (input.wasReleased(MouseButtons.LEFT) && !topPanel.getBoundingBox().intersects(mousePos)
                && !botPanel.getBoundingBox().intersects(mousePos)) {
            towers.add(new SuperTank(mousePos, SUPERTANK));
            placingSuperTank = false;
        }
        }
    // Updates towers
        for (int i = towers.size() - 1; i >= 0; i--) {
            Sprite t = towers.get(i);
            t.update(input);
            }
        }

    }
