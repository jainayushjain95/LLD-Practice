package api;


import boards.TicTacToeBoard;
import game.Board;
import game.GameState;

import java.util.function.BiFunction;
import java.util.function.Function;

public class RuleEngine {

    public GameState getState(Board board) {
        if(board instanceof TicTacToeBoard) {
            TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;

            GameState rowWin = outerTraversal((i, j) -> ticTacToeBoard.getSymbol(i, j));
            if (rowWin.isOver()) return rowWin;

            GameState colWin = outerTraversal((i, j) -> ticTacToeBoard.getSymbol(j, i));
            if (colWin.isOver()) return colWin;

            GameState diagWin = findDiagStreak(i -> ticTacToeBoard.getSymbol(i, i));
            if (diagWin.isOver()) return diagWin;

            GameState reverseDiagWin = findDiagStreak(i -> ticTacToeBoard.getSymbol(i, 2 - i));
            if (reverseDiagWin.isOver()) return reverseDiagWin;

            int countOfFilledCells = 0;
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(!ticTacToeBoard.getSymbol(i, j).equals("-")) {
                        countOfFilledCells++;
                    }
                }
            }

            if(countOfFilledCells == 9) {
                return new GameState(true, "-");
            } else {
                return new GameState(false, "-");
            }
        } else {
            return new GameState(false, "-");
        }
    }

    private GameState findDiagStreak(Function<Integer, String> traversal) {
        return traverse(traversal);
    }

    private GameState outerTraversal(BiFunction<Integer, Integer, String> next) {
        GameState gameState = new GameState(false, "-");
        for (int i = 0; i < 3; i++) {
            final int ii = i;
            GameState traversal = traverse(j-> next.apply(ii, j));
            if (traversal.isOver()) {
                gameState = traversal;
            }
        }
        return gameState;
    }

    private GameState traverse(Function<Integer, String> traversal) {
        GameState gameState = new GameState(false, "-");
        boolean possibleStreak = true;
        for (int j = 0; j < 3; j++) {
            if (traversal.apply(j).equals("-") || !traversal.apply(0).equals(traversal.apply(j))) {
                possibleStreak = false;
                break;
            }
        }
        if (possibleStreak) {
            gameState = new GameState(true, traversal.apply(0));
        }
        return gameState;
    }


}
