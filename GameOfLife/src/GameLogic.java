public class GameLogic {
    static int countLiveNeighbourCells(int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if ((i == row && j == col) || i < 0 || j < 0 || i >= BoardPanel.getROWS() || j >= BoardPanel.getCOLS()) {
                    continue;
                }
                if (BoardPanel.getBoard()[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
    }
}
