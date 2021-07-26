import bagel.util.Point;
import bagel.util.Vector2;

public class FireBall extends Ball{
    /**
     * construct fire ball based on the coordinated and the inherited direciton
     * @param point - an object consists of x and y coordinates
     * @param direction - an vector object that has direction of travel
     */
    public FireBall(Point point, Vector2 direction){
        super(point, direction, "res/fireball.png");
    }

    /**
     * update the location of the ball and its state of interactions
     */
    @Override
    public void update() {
        super.update();
    }
}
