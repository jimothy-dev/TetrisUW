package playGame.layout;

import static model.Board.PROPERTY_ROW_CLEARED;
import static model.Board.PROPERTY_NEXT_PIECE_CHANGES;
import static model.Board.PROPERTY_NEW_GAME;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Panel to display player's score.
 * Implements PCL to update score.
 *
 * @author James
 * @author Tyler
 * @author Josh
 * @version 3.0
 */
public final class ScorePanel extends JPanel implements PropertyChangeListener {

    /**
     * used for switch statement for when 1 row is cleared.
     */
    private static final int ONE = 1;

    /**
     * used for switch statement for when 2 rows are cleared.
     */
    private static final int TWO = 2;

    /**
     * used for switch statement for when 3 rows are cleared.
     */
    private static final int THREE = 3;

    /**
     * used for switch statement for when 4 rows are cleared,
     * and also to calculate timer delay on level up
     */
    private static final int FOUR = 4;

    /**
     * seperator for text labels.
     */
    private static final int TEXT_SEPERATOR = 5;

    /**
     * seperator for level up text.
     */
    private static final int LEVEL_UP_SEPERATOR = 7;

    /**
     * amount of points awarded for dropping a piece.
     */
    private static final int SCORE_FOR_PIECE_DROPPED = 4;

    /**
     * x coord of score text.
     */
    private static final int TEXT_X = 5;

    /**
     * y coord of score text.
     */
    private static final int TEXT_Y = 25;

    /**
     * Size of score text.
     */
    private static final int TEXT_SIZE = 20;

    /**
     * Score gained for one row cleared.
     */
    private static final int ONE_LINE_CLEARED = 40;
    /**
     * Score gained for two rows cleared.
     */
    private static final int TWO_LINES_CLEARED = 100;
    /**
     * Score gained for three rows cleared.
     */
    private static final int THREE_LINES_CLEARED = 300;
    /**
     * Score gained for four rows cleared.
     */
    private static final int FOUR_LINES_CLEARED = 1200;

    /**
     * Level up every 5 rows cleared.
     */
    private static final int LEVEL_UP = 5;

    /**
     * used in calculating timer delay change on level up.
     */
    private static final int LEVEL_UP_TIMER_CHANGE = 2;

    /**
     * Ensures only one panel is instantiated.
     */
    private static int count;

    /**
     * Score of the player. Updates when rows are cleared.
     */
    private int myScore;

    /**
     * Number of lines cleared by the player.
     */
    private int myLinesCleared;

    /**
     * The level of the game.
     */
    private int myLevel;

    /**
     * Timer to manage game updates at regular intervals.
     */
    private final Timer myGameTimer;


    /**
     * Panel used to display the player's score.
     * Sets background color.
     *
     * @throws IllegalArgumentException if more than one ScorePanel is instantiated.
     * @param theGameTimer Timer that runs animation of the game.
     */
    @SuppressWarnings("AssignmentToStaticFieldFromInstanceMethod")
    /*
     * warning is suppressed because count is used to ensure only one
     * ScorePanel is instantiated.
     */
    public ScorePanel(final Timer theGameTimer) {
        super();

        if (count > 0) {
            throw new IllegalArgumentException("Only one ScorePanel allowed");
        }
        count++;

        myGameTimer = theGameTimer;
        setUpScoresAndLevel();

        setLayout(new BorderLayout());
        final JPanel scorePanel = new JPanel(new FlowLayout());
        scorePanel.setOpaque(false);
    }

    /**
     * Sets up the scores and level.
     */
    private void setUpScoresAndLevel() {
        myScore = 0;
        myLinesCleared = 0;
        myLevel = 1;

    }

    @SuppressWarnings("PublicMethodNotExposedInInterface")
    /*
     * warning is suppressed because paint component is inherited from JComponent
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        final int w = getWidth();
        final int h = getHeight();
        final Color color1 = Color.WHITE;
        final Color color2 = Color.PINK;
        final GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        g2d.setPaint(Color.BLACK);
        showScores(theGraphics);
    }

    /**
     * Displays player's score in score panel.
     *
     * @param theGraphics Graphics object used to display text.
     */
    private void showScores(final Graphics theGraphics) {
        theGraphics.setFont(new Font("" + theGraphics.getFont(), Font.PLAIN, TEXT_SIZE));
        theGraphics.drawString("Score: " + myScore, TEXT_X, TEXT_Y + TEXT_SEPERATOR);
        theGraphics.drawString("Level:  " + myLevel, TEXT_X, TEXT_Y + TEXT_SIZE
                + TEXT_SEPERATOR * 2);
        theGraphics.drawString("Lines:  " + myLinesCleared, TEXT_X, TEXT_Y + TEXT_SIZE * 2
                + TEXT_SEPERATOR * THREE);

        theGraphics.setFont(new Font("" + theGraphics.getFont(), Font.PLAIN, TEXT_SIZE
                - LEVEL_UP_SEPERATOR));
        theGraphics.drawString("Next level in " + (LEVEL_UP - myLinesCleared % LEVEL_UP)
                + " lines", TEXT_X, TEXT_Y + TEXT_SIZE * THREE + TEXT_SEPERATOR * THREE);
    }




    /**
     * Updates the score when a row is cleared.
     *
     * @param theNumberOfRowsCleared int of rows cleared to keep game score.
     */
    private void calculateScore(final int theNumberOfRowsCleared) {
        final int score = switch (theNumberOfRowsCleared) {
            case ONE -> ONE_LINE_CLEARED;
            case TWO -> TWO_LINES_CLEARED;
            case THREE -> THREE_LINES_CLEARED;
            case FOUR -> FOUR_LINES_CLEARED;
            default -> throw new IllegalStateException("Unexpected value: "
                    + theNumberOfRowsCleared);
        };
        myScore += score * myLevel;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PROPERTY_ROW_CLEARED.equals(theEvent.getPropertyName())) {
            final int rowsCleared = (int) theEvent.getNewValue();

            if (rowsCleared > 0) {
                myLinesCleared += rowsCleared;
                calculateScore(rowsCleared);

                final int linesNeededForNextLevel = LEVEL_UP - (myLinesCleared % LEVEL_UP);

                final int previousLevel = myLevel;
                myLevel = myLinesCleared / LEVEL_UP + 1;

                if (myLevel > previousLevel) {
                    final int newDelay = myGameTimer.getDelay() / LEVEL_UP_TIMER_CHANGE
                            + myGameTimer.getDelay() / FOUR;
                    myGameTimer.setDelay(Math.max(newDelay, 0));
                }

                repaint();
            }
        }

        if (PROPERTY_NEW_GAME.equals(theEvent.getPropertyName())) {
            myScore = 0;
            myLinesCleared = 0;
            myLevel = 1;
            repaint();
        }

        if (PROPERTY_NEXT_PIECE_CHANGES.equals(theEvent.getPropertyName())) {
            myScore += SCORE_FOR_PIECE_DROPPED;
        }
    }
}