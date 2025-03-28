package boards;

import game.Board;
import game.Cell;

import java.util.Arrays;

public class TicTacToeBoard extends Board {
    private String[][] cells = new String[3][3];

    public TicTacToeBoard() {
       for (int i = 0; i < 3; i++) {
           for (int j = 0; j < 3; j++) {
               cells[i][j] = "-";
           }
       }
    }

    public String getCell(int row, int col) {
        return cells[row][col];
    }

    public void setCell(Cell cell, String symbol) {
        cells[cell.getRow()][cell.getCol()] = symbol;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result += getCell(i, j) + " ";
            }
            result += "\n";
        }
        return result;
    }
}
