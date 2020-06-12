package lists;

import bagel.Input;
import bagel.util.Point;

public abstract class Tower extends Sprite {

    private final Point location;
    private boolean shooting;

    public Tower(Point location, String image){
        super(location, image);
        this.location = location;
        this.shooting = false;
    }


    @Override
    public void update(Input input){
    }

    public boolean isShooting() { return shooting; }
}
