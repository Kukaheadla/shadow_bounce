/**
 * SWEN20003 Object Oriented Software Development
 * Project 2, Semester 2, 2019
 *
 * @author 815565
 * @Reference Eleanor McMurtry's Sample Solution
 */

import bagel.util.Point;

abstract class Peg extends GameObject {
    private boolean destroyed = false;
    private String shape;

    /**
     * construct the peg based on its coordinates, the shape input and reference to image sources
     * @param point - an object consists of x & y coordinates
     * @param shape - specify the shape of the image file
     * @param imageSrc - source of the image
     */
    public Peg(Point point, String shape, String imageSrc) {
        super(point, imageSrc);
        this.shape = shape;
    }

    /**
     * getter function that return the shape of the class for relevant peg color conversion
     * @return the shape of peg class
     */
    public String getShape() {
        return shape;
    }

    /**
     * Check whether the peg is destroyed for later collection removal.
     * @return true/false of whether the peg is destroyed
     */
    public boolean isDestroyed(){
        return destroyed;
    }

    /**
     * change from not destroy to destroyed
     */
    public void setDestroyed(){
        this.destroyed = true;
    }

    /**
     * update peg positions on the game screen
     */
    @Override
    public void update() {
        super.draw();
    }
}
