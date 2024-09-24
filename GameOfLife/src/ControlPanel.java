import javax.swing.*;
import java.awt.*;

public class ControlPanel {
    static JPanel panel;
    private final JSlider speedSlider;
    private static JComboBox<String> gridSizeComboBox;
    private final String[] gridSizeNames = {"Tiny", "Small", "Medium", "Large", "Experimental"};
    private final JButton startStopButton;
    private final JButton darkModeButton;
    private final JButton gridBorderButton;
    private final JButton randomizeButton;
    private final JButton clearButton;
    private final JButton exitButton;
    private static JLabel generationsLabel;
    private static JLabel populationLabel;
    private static JSpinner overpopulationSpinner;
    private static JSpinner survivalSpinner;
    private static boolean gridBorder = false;
    private boolean running = false;
    private Thread gameThread;

    public ControlPanel() {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(Theme.isDarkTheme() ? Color.BLACK : Color.WHITE);
        panel.setFocusable(false);
        speedSlider = new JSlider(30, 400);
        speedSlider.setFocusable(false);
        speedSlider.setInverted(true);
        speedSlider.setValue(100);
        speedSlider.setBackground(Color.WHITE);
        gridSizeComboBox = new JComboBox<>(gridSizeNames);
        gridSizeComboBox.setFocusable(false);
        gridSizeComboBox.setSelectedItem("Medium");
        gridSizeComboBox.addActionListener(e -> BoardPanel.changeGridSize());
        startStopButton = new JButton("Start");
        startStopButton.setFocusable(false);
        darkModeButton = new JButton("Dark Mode");
        darkModeButton.setFocusable(false);
        darkModeButton.addActionListener(e -> Theme.toggleTheme());
        gridBorderButton = new JButton("Grid Border");
        gridBorderButton.setFocusable(false);
        gridBorderButton.addActionListener(e -> toggleGridBorder());
        randomizeButton = new JButton("Randomize");
        randomizeButton.setFocusable(false);
        randomizeButton.addActionListener(e -> BoardPanel.initBoard());
        clearButton = new JButton("Clear");
        clearButton.setFocusable(false);
        clearButton.addActionListener(e -> BoardPanel.clearBoard());
        exitButton = new JButton("Exit");
        exitButton.setFocusable(false);
        exitButton.addActionListener(e -> System.exit(0));

        generationsLabel = new JLabel("Generations: 0");
        panel.add(generationsLabel);

        populationLabel = new JLabel("Population: 0");
        panel.add(populationLabel);

        JLabel overpopulationLabel = new JLabel("Overpopulation: ");
        overpopulationSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 8, 1));
        JLabel survivalLabel = new JLabel("Survival: ");
        survivalSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 8, 1));

        startStopButton.addActionListener(e -> {
            if (!running) {
                running = true;
                startStopButton.setText("Stop");
                startStopButton.setBackground(Color.RED);
                gameThread = new Thread(() -> {
                    while (running) {
                        BoardPanel.updateBoard();
                        try {
                            int selectedSpeed = speedSlider.getValue();
                            Thread.sleep(selectedSpeed);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        BoardPanel.getPanel().repaint();
                        updatePopulationLabel();
                    }
                });
                gameThread.start();
            } else {
                running = false;
                startStopButton.setText("Start");
                startStopButton.setBackground(Color.GREEN);
            }
        });

        panel.add(new JLabel("Speed: "));
        panel.add(speedSlider);
        panel.add(new JLabel("Grid size: "));
        panel.add(gridSizeComboBox);
        panel.add(startStopButton);
        panel.add(darkModeButton);
        panel.add(gridBorderButton);
        panel.add(randomizeButton);
        panel.add(clearButton);
        panel.add(exitButton);
        panel.add(overpopulationLabel);
        panel.add(overpopulationSpinner);
        panel.add(survivalLabel);
        panel.add(survivalSpinner);
    }

    static void updatePopulationLabel() {
        int populationCounter = 0;
        for (int[] row : BoardPanel.getBoard()) {
            for (int cell : row) {
                if (cell == 1) {
                    populationCounter++;
                }
            }
        }
        populationLabel.setText("Population: " + populationCounter);
    }

    private void toggleGridBorder() {
        gridBorder = !gridBorder;
        BoardPanel.getPanel().repaint();
    }

    public static JComboBox<String> getGridSizeComboBox() {
        return gridSizeComboBox;
    }

    public static JLabel getGenerationsLabel() {
        return generationsLabel;
    }

    public void setGenerationsLabel(JLabel generationsLabel) {
        this.generationsLabel = generationsLabel;
    }

    public JLabel getPopulationLabel() {
        return populationLabel;
    }

    public void setPopulationLabel(JLabel populationLabel) {
        this.populationLabel = populationLabel;
    }

    public static JSpinner getOverpopulationSpinner() {
        return overpopulationSpinner;
    }

    public void setOverpopulationSpinner(JSpinner overpopulationSpinner) {
        this.overpopulationSpinner = overpopulationSpinner;
    }

    public static boolean isGridBorder() {
        return gridBorder;
    }

    public void setGridBorder(boolean gridBorder) {
        this.gridBorder = gridBorder;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Thread getGameThread() {
        return gameThread;
    }

    public void setGameThread(Thread gameThread) {
        this.gameThread = gameThread;
    }

    public static JPanel getPanel() {
        return panel;
    }

    public static JSpinner getSurvivalSpinner() {
        return survivalSpinner;
    }

    public void setSurvivalSpinner(JSpinner survivalSpinner) {
        this.survivalSpinner = survivalSpinner;
    }
}
