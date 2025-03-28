package boards;

import game.Board;

public class TicTacToeBoard extends Board {
    private String[][] cells = new String[3][3];

    public String getCell(int row, int col) {
        return cells[row][col];
    }
}
