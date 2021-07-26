import bagel.util.Point;

/**
 * A red peg class
 */
public class RedPeg extends Peg {
    /**
     * constructing red pegs
     * @param point an object that specifies the coordinate of the class
     * @param shape a string that consist of the shape of the peg which is to be added to the image
     */
    public RedPeg(Point point, String shape) {
        super(point, shape, "res/red-"+shape+"peg.png");
    }
}
