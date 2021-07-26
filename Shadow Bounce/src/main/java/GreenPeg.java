import bagel.util.Point;

/**
 * Green peg class
 */
public class GreenPeg extends Peg{
    /**
     * constructing green pegs
     * @param point an object that specifies the coordinate of the class
     * @param shape a string that consist of the shape of the peg
     */
    public GreenPeg(Point point, String shape) {
        super(point, shape, "res/green-"+shape+"peg.png");
    }
}
