package lists;

import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.util.Point;

public class SuperTank extends Tower {

    private static final String IMAGE_FILE = "res/images/supertank.png";
    private static final String PROJ_FILE = "res/images/supertank_projectile.png";
    private static final double RADIUS = 150;
    private static final double DAMAGE = 3;
    private static final double COOLDOWN = 500;
    private static final double COST = 600;
    private final Point location;
    private boolean shooting;
    private final Image image;

    public SuperTank(Point location, String filename) {
        super(location, IMAGE_FILE);
        this.image = new Image(IMAGE_FILE);
        this.location = location;
        this.shooting = false;
    }

    @Override
    public void update(Input input){
        image.draw(getCenter().x, getCenter().y, new DrawOptions().setRotation(0));
    }

    public boolean isFinished() {
        return false;
    }
}