package boards;


import game.*;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TicTacToeBoard implements CellBoard {
    private String[][] cells = new String[3][3];
    private History history = new History();

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
    public TicTacToeBoard move(Move move) {
        history.add(this);
        TicTacToeBoard ticTacToeBoard = copy();
        ticTacToeBoard.setCell(move.getCell(), move.getPlayer().symbol());
        return ticTacToeBoard;
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

    public static RuleSet<TicTacToeBoard> getRules() {
        RuleSet<TicTacToeBoard> rules = new RuleSet<>();
        rules.add(new Rule(board -> outerTraversals((i, j) -> board.getSymbol(i, j))));
        rules.add(new Rule(board -> outerTraversals((i, j) -> board.getSymbol(j, i))));
        rules.add(new Rule(board -> traverse(i -> board.getSymbol(i, i))));
        rules.add(new Rule(board -> traverse(i -> board.getSymbol(i, 2 - i))));
        rules.add(new Rule(board -> {
            int countOfFilledCells = 0;
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(!board.getSymbol(i, j).equals("-")) {
                        countOfFilledCells++;
                    }
                }
            }

            if(countOfFilledCells == 9) {
                return new GameState(true, "-");
            }
            return new GameState(false, "-");
        }));

        return rules;
    }

    private static GameState outerTraversals(BiFunction<Integer, Integer, String> next) {
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

    private static GameState traverse(Function<Integer, String> traversal) {
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

class History {
    List<Board> boards = new ArrayList<>();

    public Board getBoardAtMove(int moveIndex) {
        for(int i = 0; i < boards.size() - (moveIndex + 1); i++) {
            boards.remove(boards.size() - 1);
        }
        return boards.get(moveIndex);
    }

    public Board undo() {
        if (boards.isEmpty()) {
            throw new IllegalStateException();
        }
        boards.remove(boards.size() - 1);
        return boards.get(boards.size() - 1);
    }

    public void add(TicTacToeBoard ticTacToeBoard) {
        boards.add(ticTacToeBoard);
    }
}
