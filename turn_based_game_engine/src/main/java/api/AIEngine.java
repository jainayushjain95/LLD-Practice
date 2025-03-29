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
            Move suggestion;
            int threshold = 3;
            if(countMoves(ticTacToeBoard) < threshold) {
                suggestion = getBasicMove(computer, ticTacToeBoard);   
            } else {
                suggestion = getSmartMove(computer, ticTacToeBoard);
            }
            if (suggestion != null) {
                return suggestion;
            }
            throw new IllegalStateException();
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Move getSmartMove(Player player, TicTacToeBoard ticTacToeBoard) {
        RuleEngine ruleEngine = new RuleEngine();

        //Victorious Moves
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(ticTacToeBoard.getSymbol(i, j).equals("-")) {
                    Move move = new Move(new Cell(i, j), player);
                    TicTacToeBoard boardCopy = ticTacToeBoard.copy();
                    boardCopy.move(move);
                    if(ruleEngine.getState(boardCopy).isOver()) {
                        return move;
                    }
                }
            }
        }

        //Defensive Moves
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(ticTacToeBoard.getSymbol(i, j).equals("-")) {
                    Move move = new Move(new Cell(i, j), player.flip());
                    TicTacToeBoard boardCopy = ticTacToeBoard.copy();
                    boardCopy.move(move);
                    if(ruleEngine.getState(boardCopy).isOver()) {
                        return new Move(new Cell(i, j), player);
                    }
                }
            }
        }
        return getBasicMove(player, ticTacToeBoard);
    }

    private int countMoves(TicTacToeBoard board) {
        int count = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(!board.getSymbol(i, j).equals("-")) {
                    count++;
                }
            }
        }
        return count;
    }

    private Move getBasicMove(Player computer, TicTacToeBoard ticTacToeBoard) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(ticTacToeBoard.getSymbol(i, j).equals("-")) {
                    return new Move(new Cell(i, j), computer);
                }
            }
        }
        return null;
    }

}
