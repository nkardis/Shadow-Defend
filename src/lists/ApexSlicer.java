package lists;

import bagel.Input;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.List;


public class ApexSlicer extends Sprite{
    private static final String IMAGE_FILE = "res/images/apexslicer.png";
    private static final double SPEED = 0.75;
    private static final double REWARD = 150;
    private static final double HEALTH = 25;
    private static final int LIFE_LOSS = 16;

    private final List<Point> polyline;
    private int targetPointIndex;
    private boolean finished;
    private boolean hit;

    public ApexSlicer(List<Point> polyline) {
        super(polyline.get(0), IMAGE_FILE);
        this.polyline = polyline;
        this.targetPointIndex = 1;
        this.finished = false;
    }

    @Override
    public void update(Input input) {
        if (finished) {
            return;
        }
        // Obtain where we currently are, and where we want to be
        Point currentPoint = getCenter();
        Point targetPoint = polyline.get(targetPointIndex);
        // Convert them to vectors to perform some very basic vector math
        Vector2 target = targetPoint.asVector();
        Vector2 current = currentPoint.asVector();
        Vector2 distance = target.sub(current);
        // Distance we are (in pixels) away from our target point
        double magnitude = distance.length();
        // Check if we are close to the target point
        if (magnitude < SPEED * ShadowDefend.getTimescale()) {
            // Check if we have reached the end
            if (targetPointIndex == polyline.size() - 1) {
                finished = true;
                return;
            } else {
                // Make our focus the next point in the polyline
                targetPointIndex += 1;
            }
        }
        // Move towards the target point
        // We do this by getting a unit vector in the direction of our target, and multiplying it
        // by the speed of the slicer (accounting for the timescale)
        super.move(distance.normalised().mul(SPEED * ShadowDefend.getTimescale()));
        // Update current rotation angle to face target point
        setAngle(Math.atan2(targetPoint.y - currentPoint.y, targetPoint.x - currentPoint.x));
        super.update(input);
    }
    public boolean isFinished() {
        return finished;
    }
}


