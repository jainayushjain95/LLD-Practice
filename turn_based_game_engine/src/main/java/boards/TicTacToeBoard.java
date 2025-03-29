package boards;


import game.Board;
import game.Cell;
import game.Move;

public class TicTacToeBoard implements Board {
    private String[][] cells = new String[3][3];

    public TicTacToeBoard() {
       for (int i = 0; i < 3; i++) {
           for (int j = 0; j < 3; j++) {
               cells[i][j] = "-";
           }
       }
    }

    public String getSymbol(int row, int col) {
        return cells[row][col];
    }

    private void setCell(Cell cell, String symbol) {
        if(cells[cell.getRow()][cell.getCol()].equals("-")) {
            cells[cell.getRow()][cell.getCol()] = symbol;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void move(Move move) {
        setCell(move.getCell(), move.getPlayer().symbol());
    }

    /*
        Decoupling:
            Since the Board interface is used to define the copy behavior, the code is decoupled from specific implementations,
            adhering to the Open/Closed Principle.
     */
    @Override
    public TicTacToeBoard copy() {
        TicTacToeBoard newBoard = new TicTacToeBoard();
        for (int i = 0; i < 3; i++) {
            System.arraycopy(cells[i], 0, newBoard.cells[i], 0, 3);
        }
        return newBoard;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result += getSymbol(i, j) + " ";
            }
            result += "\n";
        }
        return result;
    }
}
