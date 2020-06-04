package lists;

import bagel.Input;
import bagel.util.Point;

public abstract class Tower extends Sprite {

    private final Point location;
    private static final String IMAGE_FILE = "res/images/tank.png";
    private boolean shooting;

    public Tower(Point location){
        super(location, IMAGE_FILE);
        this.location = location;
        this.shooting = false;
    }

    @Override
    public void update(Input input){

    }

    public boolean isShooting() { return shooting; }
}
