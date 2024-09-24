import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardPanel {
    private static JPanel panel;

    private static int generationCounter = 0;
    private static int ROWS = 1;
    private static int COLS = 1;
    private static int CELL_SIZE = 1;
    private static int[][] board = new int[ROWS][COLS];
    private static int[][] nextBoard = new int[ROWS][COLS];

    public BoardPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                calculateCellSize();
                int cellWidth = CELL_SIZE;
                int cellHeight = CELL_SIZE;

                int xOffset = (getWidth() - (COLS * cellWidth)) / 2;
                int yOffset = (getHeight() - (ROWS * cellHeight)) / 2;

                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLS; j++) {
                        if (board[i][j] == 1) {
                            g.setColor(Theme.isDarkTheme() ? Color.WHITE : Color.BLACK);
                            g.fillRect(j * cellWidth + xOffset, i * cellHeight + yOffset, cellWidth, cellHeight);
                        } else {
                            g.setColor(Theme.isDarkTheme() ? Color.BLACK : Color.WHITE);
                            g.fillRect(j * cellWidth + xOffset, i * cellHeight + yOffset, cellWidth, cellHeight);
                        }
                        if (ControlPanel.isGridBorder()) {
                            g.setColor(Theme.isDarkTheme() ? Color.WHITE : Color.BLACK);
                            g.drawRect(j * cellWidth + xOffset, i * cellHeight + yOffset, cellWidth, cellHeight);
                        }
                    }
                }
            }
        };
        panel.setBackground(Theme.isDarkTheme() ? Color.BLACK : Color.WHITE);
        panel.setSize(1200, 800);

        MouseAdapter leftMouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleLeftMouseEvent(e);
                ControlPanel.updatePopulationLabel();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                handleLeftMouseEvent(e);
                ControlPanel.updatePopulationLabel();
            }

            private void handleLeftMouseEvent(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    int x = e.getX();
                    int y = e.getY();

                    int cellWidth = CELL_SIZE;
                    int cellHeight = CELL_SIZE;

                    int xOffset = (panel.getWidth() - (COLS * cellWidth)) / 2;
                    int yOffset = (panel.getHeight() - (ROWS * cellHeight)) / 2;

                    int col = (x - xOffset) / cellWidth;
                    int row = (y - yOffset) / cellHeight;

                    if (col >= 0 && col < COLS && row >= 0 && row < ROWS) {
                        board[row][col] = 1;
                        panel.repaint();
                    }
                }
            }
        };

        MouseAdapter rightMouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleRightMouseEvent(e);
                ControlPanel.updatePopulationLabel();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                handleRightMouseEvent(e);
                ControlPanel.updatePopulationLabel();
            }

            private void handleRightMouseEvent(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int x = e.getX();
                    int y = e.getY();

                    int cellWidth = CELL_SIZE;
                    int cellHeight = CELL_SIZE;

                    int xOffset = (panel.getWidth() - (COLS * cellWidth)) / 2;
                    int yOffset = (panel.getHeight() - (ROWS * cellHeight)) / 2;

                    int col = (x - xOffset) / cellWidth;
                    int row = (y - yOffset) / cellHeight;

                    if (col >= 0 && col < COLS && row >= 0 && row < ROWS) {
                        board[row][col] = 0;
                        panel.repaint();
                    }
                }
            }
        };

        panel.addMouseListener(leftMouseAdapter);
        panel.addMouseMotionListener(leftMouseAdapter);
        panel.addMouseListener(rightMouseAdapter);
        panel.addMouseMotionListener(rightMouseAdapter);
    }

    void initBoard() {
        generationCounter = 0;
        ControlPanel.setGenerationsLabel(new JLabel("Generations: " + generationCounter));
        for (int i= 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = (int) (Math.random() * 2);
            }
        }
        panel.repaint();
    }

    void clearBoard() {
        generationCounter = 0;
        ControlPanel.setGenerationsLabel(new JLabel("Generations: " + generationCounter));
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = 0;
            }
        }
        panel.repaint();
    }

    void updateBoard() {
        int overpopulation = (int) ControlPanel.getOverpopulationSpinner().getValue();
        int survival = (int) ControlPanel.getSurvivalSpinner().getValue();

        generationCounter++;
        ControlPanel.setGenerationsLabel(new JLabel("Generations: " + generationCounter));
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                int liveNeighbours = GameLogic.countLiveNeighbourCells(i, j);
                if (board[i][j] == 1 && (liveNeighbours >= survival && liveNeighbours <= overpopulation)) {
                    nextBoard[i][j] = 1;
                } else if (board[i][j] == 0 && liveNeighbours == 3) {
                    nextBoard[i][j] = 1;
                } else {
                    nextBoard[i][j] = 0;
                }
            }
        }
        for (int i = 0; i < ROWS; i++) {
            System.arraycopy(nextBoard[i], 0, board[i], 0, COLS);
        }
    }

    static void changeGridSize() {
        String selectedSize = (String) ControlPanel.getGridSizeComboBox().getSelectedItem();
        switch (selectedSize) {
            case "Tiny":
                ROWS = 19;
                COLS = 34;
                break;
            case "Small":
                ROWS = 45;
                COLS = 79;
                break;
            case "Medium":
                ROWS = 66;
                COLS = 117;
                break;
            case "Large":
                ROWS = 125;
                COLS = 220;
                break;
            case "Experimental":
                ROWS = 250;
                COLS = 440;
                break;
        }
        board = new int[ROWS][COLS];
        nextBoard = new int[ROWS][COLS];
        initBoard();
        calculateCellSize();
        panel.repaint();
    }

    private static void calculateCellSize() {
        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();
        CELL_SIZE = Math.min(panelWidth / COLS, panelHeight / ROWS);
    }

    public static JPanel getPanel() {
        return panel;
    }

    public int getGenerationCounter() {
        return generationCounter;
    }

    public void setGenerationCounter(int generationCounter) {
        this.generationCounter = generationCounter;
    }

    public static int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int[][] getNextBoard() {
        return nextBoard;
    }

    public void setNextBoard(int[][] nextBoard) {
        this.nextBoard = nextBoard;
    }

    public static void setPanel(JPanel panel) {
        BoardPanel.panel = panel;
    }

    public static int getROWS() {
        return ROWS;
    }

    public static void setROWS(int ROWS) {
        BoardPanel.ROWS = ROWS;
    }

    public static int getCOLS() {
        return COLS;
    }

    public static void setCOLS(int COLS) {
        BoardPanel.COLS = COLS;
    }

    public static int getCellSize() {
        return CELL_SIZE;
    }

    public static void setCellSize(int cellSize) {
        CELL_SIZE = cellSize;
    }
}
