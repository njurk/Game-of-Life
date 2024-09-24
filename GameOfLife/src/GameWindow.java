import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private JPanel boardPanel;
    private JPanel controlPanel;

    public GameWindow() {
        setTitle("Conway's Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        boardPanel = new BoardPanel().getPanel();
        controlPanel = new ControlPanel().getPanel();

        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        pack();
        BoardPanel.changeGridSize();
        setSize(1800,1100);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JPanel getBoardPanel() {
        return boardPanel;
    }

    public void setBoardPanel(JPanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    public JPanel getControlPanel() {
        return controlPanel;
    }

    public void setControlPanel(JPanel controlPanel) {
        this.controlPanel = controlPanel;
    }
}