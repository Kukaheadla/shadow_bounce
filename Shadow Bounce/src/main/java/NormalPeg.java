import bagel.util.Point;

public class NormalPeg extends Peg {
    /**
     * constructing normal (blue peg)
     * @param point an object that specifies the coordinate of the class
     * @param shape a string that consist of the shape of the peg which is to be added to the image
     */
    public NormalPeg(Point point, String shape) {
        super(point, shape, "res/"+shape+"peg.png");
    }
}
