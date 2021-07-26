import bagel.util.Point;
import bagel.util.Vector2;

public class NormalBall extends Ball{
    /**
     * Construct a normal ball
     * @param point - an object consists of its 2 dimensional coordinates
     * @param direction - a vector that records the direction of movements
     */
    public NormalBall(Point point, Vector2 direction){
        super(point, direction, "res/ball.png");
    }

    /**
     * update the location of the ball and its state of interactions
     */
    @Override
    public void update() {
        super.update();
    }
}
