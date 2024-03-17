package playGame.paint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

/**
 * Utility class used to create shapes and prints them according to the parameters given.
 *
 * @author James Simpson jsimp33@uw.edu
 * @version 3.0
 */
public interface PaintTetromino {

    /**
     * Used to paint and fill tetrominos.
     *
     * @param theG2d Graphics 2d object used to paint.
     * @param theS1 Shape 1 to be painted.
     * @param theS2 Shape 2 to be painted.
     * @param theS3 Shape 3 to be painted.
     * @param theS4 Shape 4 to be painted.
     */
    static void paintShape(final Graphics2D theG2d,
                           final Shape theS1, final Shape theS2,
                           final Shape theS3, final Shape theS4) {
        theG2d.fill(theS1);
        theG2d.fill(theS2);
        theG2d.fill(theS3);
        theG2d.fill(theS4);

        theG2d.setPaint(Color.BLACK);
        theG2d.draw(theS1);
        theG2d.draw(theS2);
        theG2d.draw(theS3);
        theG2d.draw(theS4);
    }
}
