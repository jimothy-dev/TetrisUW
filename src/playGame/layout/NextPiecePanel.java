package playGame.layout;

import static model.Board.PROPERTY_NEXT_PIECE_CHANGES;
import static playGame.paint.PaintI.createIShape;
import static playGame.paint.PaintJ.createJShape;
import static playGame.paint.PaintL.createLShape;
import static playGame.paint.PaintO.createOShape;
import static playGame.paint.PaintS.createSShape;
import static playGame.paint.PaintT.createTShape;
import static playGame.paint.PaintZ.createZShape;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Rotation;
import model.TetrisPiece;

/**
 *Next piece panel shows the next tetromino to be played.
 *
 * @author James Simpson jsimp33@uw.edu
 * @author Tyler
 * @author Josh
 * @author Cam
 * @version 3.0
 */
public final class NextPiecePanel extends JPanel implements PropertyChangeListener {
    /**
     * The width of the I piece.
     */
    private static final int I_PIECE_WIDTH = 4;

    /**
     * The width of the J piece.
     */
    private static final int J_PIECE_WIDTH = 3;

    /**
     * The width of the L piece.
     */
    private static final int L_PIECE_WIDTH = 3;

    /**
     * The width of the O piece.
     */
    private static final int O_PIECE_WIDTH = 2;

    /**
     * The width of the S piece.
     */
    private static final int S_PIECE_WIDTH = 3;

    /**
     * The width of the T piece.
     */
    private static final int T_PIECE_WIDTH = 3;

    /**
     * The width of the Z piece.
     */
    private static final int Z_PIECE_WIDTH = 3;


    /**
     * The width of each block being displayed.
     */
    private static final int BLOCK_WIDTH = 16;

    /**
     * The height of each block being displayed.
     */
    private static final int BLOCK_HEIGHT = 16;

    /**
     * Used for the width of Graphics2d.
     */
    private static final int STROKE_WIDTH = 1;

    /**
     * The rotation of the next piece
     */
    private static final Rotation ROTATION = Rotation.NONE;

    /**
     * Ensures only one panel is instantiated.
     */
    private static int count;

    /**
     * The next piece to be displayed.
     */
    private TetrisPiece myNextTetrisPiece;

    /**
     * Panel that will show the next Tetromino to be played.
     * Sets background to assigned color.
     */
    @SuppressWarnings("AssignmentToStaticFieldFromInstanceMethod")
    /*
     * warning is suppressed because count is used to ensure only one
     * NextPiecePanel is instantiated.
     */
    public NextPiecePanel() {
        super();

        if (count > 0) {
            throw new IllegalArgumentException("Only one NextPiecePanel allowed");
        }
        count++;

        setBackground(Color.BLUE);
        final JLabel nextPieceLabel = new JLabel("Next Piece");
        add(nextPieceLabel, BorderLayout.NORTH);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PROPERTY_NEXT_PIECE_CHANGES.equals(theEvent.getPropertyName())) {
            myNextTetrisPiece = (TetrisPiece) theEvent.getNewValue();
            repaint();
        }
    }
    @SuppressWarnings("PublicMethodNotExposedInInterface")
    /*
     * warning is suppressed because paint component is inherited from JComponent
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setStroke(new BasicStroke(STROKE_WIDTH));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        final int w = getWidth();
        final int h = getHeight();
        final Color color1 = Color.PINK;
        final Color color2 = Color.WHITE;
        final GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        paintTetriminos(g2d);
    }

    /**
     * Paints the next tetrimino to be played.
     * @param theGraphics the graphics to be painted.
     */
    @SuppressWarnings("OverlyLongMethod")
    /*
     * OverlyLongMethod warning is suppressed because the switch statement
     * cannot be broken up without losing functionality.
     */
    private void paintTetriminos(final Graphics2D theGraphics) {
        if (myNextTetrisPiece != null) {
            switch (myNextTetrisPiece) {
                case I:
                    createIShape(theGraphics, BLOCK_HEIGHT, (getHeight() - BLOCK_HEIGHT) / 2
                            , (getWidth() - I_PIECE_WIDTH * BLOCK_HEIGHT) / 2, ROTATION);
                    break;
                case L:
                    createLShape(theGraphics, BLOCK_HEIGHT, (getHeight() - 2
                            * BLOCK_HEIGHT) / 2, (getWidth() - L_PIECE_WIDTH
                            * BLOCK_HEIGHT) / 2, ROTATION);
                    break;
                case J:
                    createJShape(theGraphics, BLOCK_HEIGHT, (getHeight()  - 2
                            * BLOCK_HEIGHT) / 2, (getWidth() - J_PIECE_WIDTH
                            * BLOCK_HEIGHT) / 2, ROTATION);
                    break;
                case O:
                    createOShape(theGraphics, BLOCK_HEIGHT, (getHeight()  - 2
                            * BLOCK_HEIGHT) / 2, (getWidth() - O_PIECE_WIDTH
                            * BLOCK_HEIGHT) / 2, ROTATION);
                    break;
                case S:
                    createSShape(theGraphics, BLOCK_HEIGHT, (getHeight() - 2
                            * BLOCK_HEIGHT) / 2, (getWidth() - S_PIECE_WIDTH
                            * BLOCK_HEIGHT) / 2, ROTATION);
                    break;
                case T:
                    createTShape(theGraphics, BLOCK_HEIGHT, (getHeight() - 2
                            * BLOCK_HEIGHT) / 2, (getWidth() - T_PIECE_WIDTH
                            * BLOCK_HEIGHT) / 2, ROTATION);
                    break;
                case Z:
                    createZShape(theGraphics, BLOCK_HEIGHT, (getHeight() - 2
                            * BLOCK_HEIGHT) / 2, (getWidth() - Z_PIECE_WIDTH
                            * BLOCK_HEIGHT) / 2, ROTATION);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + myNextTetrisPiece);
            }
        }
    }
}
