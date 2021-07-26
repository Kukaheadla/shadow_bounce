import bagel.util.Point;

public class GreyPeg extends Peg {
    private String shape;

    /**
     * construct a grey peg
     * @param point - a coordinates consists of x and y
     * @param shape - a string that specifies the image resources
     */
    public GreyPeg(Point point, String shape) {
        super(point, shape,"res/grey-"+shape+"peg.png");
    }

}

