package playGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import model.Board;
import model.MyDifficultyChanger;
import playGame.layout.GamePanel;
import playGame.layout.MainPanel;
import playGame.layout.NextPiecePanel;
import playGame.layout.ScorePanel;
import playGame.menu.Menu;


/**
 * This class represents the graphical user interface for the Tetris game.
 * It handles the initialization and display of the game window, including setting up
 * the game board, menu, timer, and various GUI components.
 *
 * @author Group 7
 * @version 1.0.2
 *
 */
public class TetrisGUI implements MyDifficultyChanger {

     /**
     * Sets the mode to easy difficulty (base difficulty).
     */
    public static final int EASY_DIFFICULTY = 1000;

    /**
     * Sets the mode to medium difficulty.
     */
    public static final int MEDIUM_DIFFICULTY = 500;

    /**
     * Sets the mode to hard difficulty.
     */
    public static final int HARD_DIFFICULTY = 100;

    /**
     * The size of the array of panels.
     */
    private static final int ARRAY_SIZE = 3;

    /**
     * The height of the border around the game window.
     */
    private static final int BORDER_SIZE_HEIGHT = 10;

    /**
     * The width of the border around the game window.
     */
    private static final int BORDER_SIZE_WIDTH = 10;

    /**
     * The current difficulty set to easy originally.
     */
    private int myCurrentDifficulty = EASY_DIFFICULTY;

    /**
     * The primary model object representing the Tetris game board.
     */
    private final Board myBoard;

    /**
     * next piece panel object representing the panel where the next piece is shown.
     */
    private final NextPiecePanel myNextPiecePanel;

    /**
     * game panel object representing the panel where game is played.
     */
    private final GamePanel myGamePanel;

    /**
     * score panel object representing the panel where the score is shown.
     */
    private final ScorePanel myScorePanel;

    /**
     * Timer to manage game updates at regular intervals.
     */
    private Timer myGameTimer;

    /**
     * Location of music in the file.
     */
    private File myMusicFile;

    /**
     * Location of sound effect in the file.
     */
    private File mySoundFile;

    /**
     * Constructs a new TetrisGUI object.
     * Initializes the game board, sets up GUI components, and adds necessary listeners.
     * Music-
     * Pixel Story by Roa Music | <a href="https://soundcloud.com/roa_music1031">...</a>
     * Music promoted by <a href="https://www.free-stock-music.com">...</a>
     * Creative Commons / Attribution 3.0 Unported License (CC BY 3.0)
     * <a href="https://creativecommons.org/licenses/by/3.0/deed.en_US">...</a>
     */
    public TetrisGUI() {
        super();
        constructorHelper();
        myBoard = new Board();
        myGamePanel = new GamePanel();
        myNextPiecePanel = new NextPiecePanel();
        myScorePanel = new ScorePanel(myGameTimer);
        myBoard.addPropertyChangeListener(myNextPiecePanel);
        myBoard.addPropertyChangeListener(myGamePanel);
        myBoard.addPropertyChangeListener(myScorePanel);
        setUpComponents();
    }

    /**
     * Helper method to break up constructor.
     */
    private void constructorHelper() {
        myMusicFile = new File("src/music/music.wav");
        mySoundFile = new File("src/music/jingle.wav");
        myGameTimer = new Timer(EASY_DIFFICULTY, theEvent -> myBoard.step());
    }

    /**
     * Sets up the main components of the GUI.
     * Initializes the frame, sets up the layout, adds a menu bar, main panel, and borders.
     */
    private void setUpComponents() {
        final JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        final List<File> soundList = new ArrayList<>();
        soundList.add(myMusicFile);
        soundList.add(mySoundFile);

        final JPanel[] panelArray = new JPanel[ARRAY_SIZE];
        panelArray[0] = myGamePanel;
        panelArray[1] = myNextPiecePanel;
        panelArray[2] = myScorePanel;

        final MainPanel mainPanel = new MainPanel(myBoard, myGameTimer,
                panelArray, soundList);

        frame.setJMenuBar(new Menu(myBoard, myGameTimer, this));
        frame.add(mainPanel, BorderLayout.CENTER);

        addBorders(frame);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    /**
     * Adds border panels to the frame.
     * @param theFrame The JFrame to which the borders are added.
     */
    private void addBorders(final JFrame theFrame) {
        final JPanel left = new JPanel();
        final JPanel right = new JPanel();
        final JPanel top = new JPanel();
        final JPanel bottom = new JPanel();

        left.setPreferredSize(new Dimension(BORDER_SIZE_WIDTH, BORDER_SIZE_HEIGHT));
        right.setPreferredSize(new Dimension(BORDER_SIZE_WIDTH, BORDER_SIZE_HEIGHT));
        top.setPreferredSize(new Dimension(BORDER_SIZE_WIDTH, BORDER_SIZE_HEIGHT));
        bottom.setPreferredSize(new Dimension(BORDER_SIZE_WIDTH, BORDER_SIZE_HEIGHT));

        theFrame.add(left, BorderLayout.WEST);
        theFrame.add(right, BorderLayout.EAST);
        theFrame.add(top, BorderLayout.NORTH);
        theFrame.add(bottom, BorderLayout.SOUTH);
    }

    /**
     * This method sets the new difficulty for the game,
     * adjusts the game timer's delay accordingly, restarts
     * the game board to apply the new difficulty, and restarts the game timer.
     *
     * @param theNewDifficulty The new difficulty level to set.
     *                      This should be one of the predefined constants: EASY_DIFFICULTY,
     *                      MEDIUM_DIFFICULTY, or HARD_DIFFICULTY.
     */
    @Override
    public void changeDifficulty(final int theNewDifficulty) {
        myCurrentDifficulty = theNewDifficulty;
        JOptionPane.showMessageDialog(null, "Current Difficulty: " + getCurrentDifficulty());
    }

    /**
     * This method returns a string representation of the current difficulty level.
     *
     * @return Easy / Medium / Hard / if none are chosen defaults to easy.
     */
    @Override
    public String getCurrentDifficulty() {
        return switch (myCurrentDifficulty) {
            case EASY_DIFFICULTY -> "Easy";
            case MEDIUM_DIFFICULTY -> "Medium";
            case HARD_DIFFICULTY -> "Hard";
            default -> "Default difficulty is Easy";
        };
    }

    /**
     * gets current difficulty.
     *
     * @return myCurrentDifficulty (Constant for times step is called per second).
     */
    @Override
    public int getCurrentDifficultyValue() {
        return myCurrentDifficulty;
    }

    /**
     * The main method to run the Tetris GUI.
     * @param theArgs Command line arguments (not used).
     */
    public static void main(final String[] theArgs) {
        SwingUtilities.invokeLater(TetrisGUI::new);
    }
}
