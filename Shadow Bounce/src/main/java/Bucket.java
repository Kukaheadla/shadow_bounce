import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;


public class Bucket extends GameObject {
    private Vector2 velocity;
    private static final int SPEED = 4;

    public Bucket(Point point, Vector2 direction) {
        super(point, "res/bucket.png");
        velocity = direction.mul(SPEED);
    }

    @Override
    public void update() {
        super.move(velocity);
        if (super.getRect().left() < 0 || super.getRect().right() > Window.getWidth()) {
            velocity = new Vector2(-velocity.x, velocity.y);
        }

        super.draw();
    }
}
