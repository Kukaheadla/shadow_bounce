/**
 * SWEN20003 Object Oriented Software Development
 * Project 2, Semester 2, 2019
 *
 * @author 815565
 * @Reference Eleanor McMurtry's Sample Solution
 */

import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;
import bagel.util.Side;

abstract class Ball extends GameObject {
    private Vector2 velocity;
    private static final double GRAVITY = 0.15;
    private static final double SPEED = 10;

    public Ball(Point point, Vector2 direction, String imageSrc) {
        super(point, imageSrc);
        velocity = direction.mul(SPEED);
    }

    public Vector2 getVelocity(){
        return velocity;
    }

    public boolean outOfScreen() {
        return super.getRect().top() > Window.getHeight();
    }

    public void reflection(Peg intersectedPeg) {
        Side sideOfIntersect = this.getRect().intersectedAt(this.getRect().centre(),
                intersectedPeg.getRect().centre());
        double velocityX = velocity.x;
        double velocityY = velocity.y;

        if (sideOfIntersect == Side.BOTTOM || sideOfIntersect == Side.TOP){
            velocityY = -velocityY;
        }

        if (sideOfIntersect == Side.LEFT || sideOfIntersect == Side.RIGHT){
            velocityX = -velocityX;
        }

        velocity = new Vector2(velocityX, velocityY);
    }

    @Override
    public void update() {
        velocity = velocity.add(Vector2.down.mul(GRAVITY));
        super.move(velocity);

        if (super.getRect().left() < 0 || super.getRect().right() > Window.getWidth()) {
            velocity = new Vector2(-velocity.x, velocity.y);
        }

        super.draw();
    }
}
