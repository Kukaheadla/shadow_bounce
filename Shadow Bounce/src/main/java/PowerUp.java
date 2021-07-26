import java.lang.Math;

import java.util.Random;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Side;
import bagel.util.Vector2;

public class PowerUp extends GameObject {
    private Vector2 velocity;
    private static final int MAX_CHANCE_RANGE = 9; //as rand picks num between 0..9 = 1/10 spawn chances
    private static final int SPAWN_TARGET_NO = 1;
    private static final int SPEED = 3;
    private static final int WITHIN_DESTINATION_REACH = 5;
    private Point destination;
    private Point new_destination;

    /**
     * Construct Power up function with a give spawn point and generate direction base on the random
     * destination
     * @param spawnPoint a Point at which the power point spawn
     */
    public PowerUp(Point spawnPoint) {
        super(spawnPoint,"res/powerup.png");
        destination = generateRandPoint();
        velocity = destination.asVector().sub(spawnPoint.asVector()).normalised().mul(SPEED);
    }

    /**
     * Function that generate a Point object with random x and y coordinates
     * @return - a point object
     */
    public static Point generateRandPoint(){
        Random rand = new Random();
        return new Point(rand.nextDouble()*(Window.getWidth()), rand.nextDouble()*((Window.getHeight())));
    }

    /**
     * a function that checks whether the randomly generated number(i) equals to the target number
     *         with the given range that is equivalent to 1/10 chance which a power up would
     *         created
     * @return whether the powerup can spawn
     */
    public static boolean chanceSpawn(){
        //return true or false based on probability generator
        boolean canSpawn = (SPAWN_TARGET_NO == (int)(Math.random()*(MAX_CHANCE_RANGE)));
        
        if(canSpawn){
            System.out.println("PowerUp Spawned at this turn!");
        } else {
            System.out.println("Bad luck :/ PowerUp is not yet spawnable");
        }
        return canSpawn;
    }

    /**
     * function at which the powerup seek to move another random spot
     * after reaching the destination
     */
    @Override
    public void update() {
        //pick a new destination if the old one has been reached
        if(this.getRect().centre().asVector().sub(destination.asVector()).length() < WITHIN_DESTINATION_REACH){
            new_destination = generateRandPoint();
            velocity = new_destination.asVector().sub(destination.asVector()).normalised().mul(SPEED);
            destination = new_destination;
        }

        super.move(velocity);
        super.draw();
    }
}
