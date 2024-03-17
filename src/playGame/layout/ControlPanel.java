package playGame.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Control panel shows the controls for the game.
 *
 * @author Group 7
 * @version Autumn 2023
 */
public class ControlPanel extends JPanel {
    /**
     * Width of the left panel.
     */
    private static final int LEFT_PANEL_WIDTH = 50;
    /**
     * Width of the right panel.
     */
    private static final int RIGHT_PANEL_WIDTH = 110;
    /**
     * Height of the left and right panels.
     */
    private static final int PANEL_HEIGHT = 100;
    /**
     * static int shared amongst all control panel objects to ensure that
     * there is only ever one instance.
     */
    private static int count;

    /**
     * Constructs a ControlPanel object.
     *
     * @throws IllegalArgumentException if more than one ControlPanel is instantiated.
     */
    @SuppressWarnings("AssignmentToStaticFieldFromInstanceMethod")
    /*
     * warning is suppressed because count is used to ensure only one
     * ControlPanel is instantiated.
     */
    public ControlPanel() {
        super();

        if (count > 0) {
            throw new IllegalArgumentException("Only one ControlPanel allowed");
        }
        count++;
        setUpPanel();
    }

    /**
     * Sets up the labels for the control panel.
     */
    private void setUpPanel() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setLayout(new BorderLayout());
        final JLabel controlLabel = new JLabel(" Controls:");
        add(controlLabel, BorderLayout.NORTH);

        setUpLeftPanel();
        setUpRightPanel();
    }
    private void setUpLeftPanel() {
        final JPanel leftPanel = new JPanel(new GridLayout(6, 1));
        add(leftPanel, BorderLayout.WEST);
        leftPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, PANEL_HEIGHT));
        leftPanel.setOpaque(false);

        leftPanel.add(new JLabel(" Left:"));
        leftPanel.add(new JLabel(" Right:"));
        leftPanel.add(new JLabel(" Down:"));
        leftPanel.add(new JLabel(" Rotate:"));
        leftPanel.add(new JLabel(" Drop:"));
        leftPanel.add(new JLabel(" Pause:"));
    }

    private void setUpRightPanel() {
        final JPanel rightPanel = new JPanel(new GridLayout(6, 1));
        add(rightPanel, BorderLayout.EAST);
        rightPanel.setPreferredSize(new Dimension(RIGHT_PANEL_WIDTH, PANEL_HEIGHT));
        rightPanel.setOpaque(false);

        rightPanel.add(new JLabel(" left arrow / A"));
        rightPanel.add(new JLabel(" right arrow / D"));
        rightPanel.add(new JLabel(" down arrow / S"));
        rightPanel.add(new JLabel(" up arrow / W"));
        rightPanel.add(new JLabel(" space"));
        rightPanel.add(new JLabel(" P"));
    }
}
