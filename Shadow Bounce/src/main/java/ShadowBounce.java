/**
 * SWEN20003 Object Oriented Software Development
 * Project 2, Semester 2, 2019
 *
 * @author 815565
 * @Reference Eleanor McMurtry's Sample Solution
 */

import bagel.*;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ShadowBounce extends AbstractGame {
    //Constant variables
    private static final Point BALL_POSITION = new Point(512, 32);
    private static final Point BUCKET_POSITION = new Point(512, 744);
    private static final int GREEN_PEG_MAX_SPAWN = 1;
    private static final double RED_PEG_MAX_SPAWN_RATIO = 0.2;
    private static final double FIRE_BALL_DESTRUCT_DISTANCE = 70.0;

    //functional variables
    private static Random rand = new Random();
    private int turnCount = 20;
    private int currLevel = 0;
    private boolean toggleGreenPeg = false;
    private boolean togglePowerUp = false;
    private Point toggleGreenPegPt;
    private boolean redPegRemain = true;
    private boolean bluePegRemain = true;

    //Game items
    private ArrayList<Peg> pegArr;
    private ArrayList<Ball> ballArr;
    private Bucket bucket;
    private PowerUp powerUp;

    /**
     * A function that search peg's list and randomly converts a normal peg into green peg
     * (and convert previous green peg back into a normal peg)
     * @param pegArr - A collection of pegs
     */
    private void convertGreenPeg(ArrayList<Peg> pegArr) {
        int numConverted = 0;
        int rand_elem;

        //reset green peg by setting the previous one back to normal peg
        for (int i=0; i < pegArr.size(); i++){
            if (pegArr.get(i) instanceof GreenPeg){
                Point greenPegPt = pegArr.get(i).getRect().centre();
                pegArr.set(i, new NormalPeg(greenPegPt,  pegArr.get(i).getShape()));
            }
        }

        //randomly picking the next green peg
        while(numConverted < GREEN_PEG_MAX_SPAWN){
            rand_elem = rand.nextInt(pegArr.size());
            if(pegArr.get(rand_elem) instanceof NormalPeg){
                Point pt = pegArr.get(rand_elem).getRect().centre();
                pegArr.set(rand_elem, new GreenPeg(pt, pegArr.get(rand_elem).getShape()));
                numConverted++;
            }
        }
    }

    /**
     * A function that search peg's list and randomly converts normal pegs into red peg
     * based on constant ratio specified in the Shawdow Bounce class
     * @param pegArr - A collection of pegs
     */
    private void convertRedPegs(ArrayList<Peg> pegArr) {
        int numConverted = 0;
        int rand_elem;

        while(numConverted < (int)(RED_PEG_MAX_SPAWN_RATIO*(pegArr.size()))){
            rand_elem = rand.nextInt(pegArr.size());
            if(pegArr.get(rand_elem) instanceof NormalPeg){
                Point pt = pegArr.get(rand_elem).getRect().centre();
                pegArr.set(rand_elem, new RedPeg(pt, pegArr.get(rand_elem).getShape()));
                numConverted++;
            }
        }
    }

    /**
     * Converts normal ball into fireball by extracting its point and direction
     * @param ballArr - a collection of balls
     */
    private void convertFireBall(ArrayList<Ball> ballArr) {
        for(int i=0; i < ballArr.size(); i++){
            Ball currBall = ballArr.get(i);
            ballArr.set(i,
                new FireBall(currBall.getRect().centre(),
                currBall.getVelocity().normalised())
            );
        }
    }

    /**
     * checking whether the balls collection contains fireball
     * @param ballArr collection of balls
     * @return - true or false on whether the balls collection contains fireball
     */
    private boolean containFireBall(ArrayList<Ball> ballArr){
        boolean containFB = false;
        for(Ball ballEach: ballArr){
            if (ballEach instanceof FireBall){
                containFB = true;
            }
        }
        return containFB;
    }


    /**
     * a function used when fireball(s) is activated to destroy neighbor pegs when it/they collided with
     * a target peg
     * @param selectedPeg - the peg that collided with fireball
     * @param pegArr - a collection of pegs
     */
    private void destroyNeighborPeg(Peg selectedPeg, ArrayList<Peg> pegArr) {
        Point selectedPegPt = selectedPeg.getRect().centre();
        //Computing length from peg's center to edge
        double pegLengthFromCenter = selectedPegPt.asVector()
                .sub(selectedPeg.getRect().bottomRight().asVector()).length();

        for (Iterator<Peg> iterator = pegArr.iterator(); iterator.hasNext();) {
            Peg currPeg = iterator.next();
            double distanceToTargetPeg = currPeg.getRect().centre().asVector()
                                            .sub(selectedPegPt.asVector()).length() - 2*pegLengthFromCenter;
            boolean inRange = (distanceToTargetPeg <= FIRE_BALL_DESTRUCT_DISTANCE);
            if (inRange && !(currPeg instanceof GreyPeg)) {
                currPeg.setDestroyed();
            }
        }
    }

    /**
     *
     * @param level - based on the title of CSV files
     * @return a collection of pegs that is being read from the files
     */
    private ArrayList<Peg> initializeBoard(int level){
        ArrayList<Peg> pegArr = CSVReader.readPegs(level);
        convertRedPegs(pegArr);
        convertGreenPeg(pegArr);
        return pegArr;
    }

    /**
     * Construct Shadow bounce class
     */
    public ShadowBounce() {
        pegArr = initializeBoard(currLevel);
        ballArr = new ArrayList<>();
        bucket = new Bucket(BUCKET_POSITION, Vector2.left);
        powerUp = (PowerUp.chanceSpawn())? (new PowerUp(PowerUp.generateRandPoint())): null;
    }

    /**
     * Update function that exhibits the interactions between classes of object
     * @param input - device input that triggers the ball to shoot
     */
    @Override
    protected void update(Input input) {
        redPegRemain = false;
        bluePegRemain = false;
        // Check all non-deleted pegs for intersection with the each ball
        for (Iterator<Peg> pegIterator = pegArr.iterator(); pegIterator.hasNext();) {
            Peg pegEach = pegIterator.next();
            pegEach.update();

            for (Ball eachBall : ballArr) {
                if (eachBall != null && eachBall.intersects(pegEach)) {
                    eachBall.reflection(pegEach);

                    //Destroy neighbor peg in range when fireball is hitting the peg
                    if (eachBall instanceof FireBall) {
                        destroyNeighborPeg(pegEach, pegArr);
                    }

                    //Destroy the peg iff peg is not grey
                    if (!(pegEach instanceof GreyPeg)) {
                        pegEach.setDestroyed();
                    }
                }
            }
            //consider effect of green peg after it been hit
            if(pegEach.isDestroyed() && (pegEach instanceof GreenPeg)) {
                System.out.println("Hit a green peg!");
                toggleGreenPeg = true;
                toggleGreenPegPt = pegEach.getRect().centre();
                pegIterator.remove();
            } else if (pegEach.isDestroyed()){
                pegIterator.remove();
            }

            if (pegEach instanceof RedPeg) {
                redPegRemain = true;
            }

            if(pegEach instanceof NormalPeg){
                bluePegRemain = true;
            }
        }

        // If we don't have a ball and the mouse button was clicked, create one
        if (input.wasPressed(MouseButtons.LEFT) && ballArr.isEmpty() && turnCount > 0) {
            ballArr.add(new NormalBall(BALL_POSITION, input.directionToMouse(BALL_POSITION)));
        }

        if (!(ballArr.isEmpty())) {
            for(int i=0; i<ballArr.size(); ++i){
                ballArr.get(i).update();
                if (ballArr.get(i).outOfScreen()) {

                    //respawn green peg if there are no balls left on screen
                    if(ballArr.size() == 1){
                        turnCount--;
                        System.out.printf("%d turns left\n", turnCount);
                        if(bluePegRemain){
                            convertGreenPeg(pegArr);
                        }
                        powerUp = (PowerUp.chanceSpawn())? (new PowerUp(PowerUp.generateRandPoint())): null;
                    }

                    // Delete the ball when it leaves the screen
                    ballArr.remove(i);
                }
            }

            //Increment turn count if the ball touches the bucket
            for(int i=0; i<ballArr.size(); ++i) {
                //convert balls to fireball if any ball hits the power-up
                if(powerUp != null && ballArr.get(i).intersects(powerUp)){
                    System.out.printf("Hit a power up.");
                    togglePowerUp = true;
                }

                //increment turn if any ball hits the bucket
                if (ballArr.get(i).intersects(bucket)) {
                    turnCount++;
                    System.out.println("Bucket! Now you got +1 turn!");
                    ballArr.remove(i);
                }
            }
        }

        //spawn multiple balls after triggering green peg
        if (toggleGreenPeg && containFireBall(ballArr)) {
            ballArr.add(new FireBall(toggleGreenPegPt, Vector2.down.add(Vector2.left)));
            ballArr.add(new FireBall(toggleGreenPegPt, Vector2.down.add(Vector2.right)));
            toggleGreenPeg = false;
        } else if (toggleGreenPeg) {
            ballArr.add(new NormalBall(toggleGreenPegPt, Vector2.down.add(Vector2.left)));
            ballArr.add(new NormalBall(toggleGreenPegPt, Vector2.down.add(Vector2.right)));
            toggleGreenPeg = false;
        }

        //convert fireball once powerup is hit
        if(togglePowerUp){
            System.out.printf("it is bout to get destructive!\n");
            convertFireBall(ballArr);
            togglePowerUp = false;
            powerUp = null;
        }

        bucket.update();
        if (powerUp != null){
            powerUp.update();
        }

        //advance to next level if there're no red pegs remaining
        if (ballArr.isEmpty()){
            if(!(redPegRemain) && currLevel < 4){
                System.out.printf("Finished level %d, now to level %d\n", currLevel, currLevel+1);
                pegArr = initializeBoard(++currLevel);
                turnCount = 20;
            }
        }
    }

    /**
     * Main function that runs shadow bounce
     * @param args main function parameter
     */
    public static void main(String[] args) {
        new ShadowBounce().run();
    }
}
