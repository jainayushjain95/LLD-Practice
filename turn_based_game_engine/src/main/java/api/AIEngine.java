package api;


import boards.TicTacToeBoard;
import game.Board;
import game.Cell;
import game.Move;
import game.Player;

public class AIEngine {

    public Move suggestMove(Player computer, Board board) {
        if(board instanceof TicTacToeBoard) {
            TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(ticTacToeBoard.getCell(i, j).equals("-")) {
                        return new Move(new Cell(i, j), computer);
                    }
                }
            }
            throw new IllegalArgumentException();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
