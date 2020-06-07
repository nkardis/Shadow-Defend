package lists;

import bagel.*;
import bagel.util.Colour;

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

    }

    /**
     *  Finds y-coord for status panel location
     * @return y coordinate for status panel
     */
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
        } else {
            return WAITING;
        }
    }

    public int getLives() {
            return lives;
        }

    public void decreaseLives() {lives--;}

    private void drawBuyPanel(){
        /* Top panel draw*/
        topPanel.drawFromTopLeft(0,0);
        tank.draw(LEFT_SPACING, getItemVerticalOffset());
        priceFont.drawString(String.valueOf("$" + TANKPRICE),LEFT_SPACING/2, getPriceVerticalOffset(),
                new DrawOptions().setBlendColour(towerPurchaseable(TANKPRICE)));
        superTank.draw(LEFT_SPACING + GAP_SPACING, getItemVerticalOffset());
        priceFont.drawString(String.valueOf("$" + SUPERTANKPRICE), LEFT_SPACING/2 + GAP_SPACING, getPriceVerticalOffset(),
                new DrawOptions().setBlendColour(towerPurchaseable(SUPERTANKPRICE)));
        airSupport.draw(LEFT_SPACING + 2*GAP_SPACING, getItemVerticalOffset());
        priceFont.drawString(String.valueOf("$" + AIRSUPPORTPRICE), LEFT_SPACING/2 + 2*GAP_SPACING, getPriceVerticalOffset(),
                new DrawOptions().setBlendColour(towerPurchaseable(AIRSUPPORTPRICE)));
        moneyFont.drawString(String.valueOf(money), getMoneyOffset(), TOP_SPACING);
        bindFont.drawString(BINDS, topPanel.getWidth()/2 - bindFont.getWidth(BINDS)/2, getItemVerticalOffset());
    }

    private void drawStatusPanel(){
        /* Status panel draw */
        botPanel.drawFromTopLeft(0, getStatusPanelLocation());
        statusPanelFont.drawString(String.valueOf("Wave: " + ShadowDefend.getWaveNumber()),5,
                statusPanelCentering());
        statusPanelFont.drawString(String.valueOf("Time Scale: " + ShadowDefend.getTimescale()), 5 + 2*GAP_SPACING,
                statusPanelCentering(), new DrawOptions().setBlendColour(timeScaleColour(ShadowDefend.getTimescale())));
        statusPanelFont.drawString(waveStatus(),
                botPanel.getWidth()/2 - statusPanelFont.getWidth(waveStatus())/2,statusPanelCentering());
        statusPanelFont.drawString("Lives: " + getLives(),
                botPanel.getWidth() - (statusPanelFont.getWidth("Lives: " + getLives()) + 5), statusPanelCentering());
    }

    public void update(Input input) {
    drawBuyPanel();
    drawStatusPanel();
    }
}
