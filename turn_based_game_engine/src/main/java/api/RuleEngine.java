package api;


import boards.TicTacToeBoard;
import game.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class RuleEngine {

    Map<String, List<Rule<TicTacToeBoard>>> ruleMap = new HashMap<>();

    public RuleEngine() {
        String key = TicTacToeBoard.class.getName();
        ruleMap.put(key, new ArrayList<>());
        ruleMap.get(key).add(new Rule<>(board -> outerTraversals((i, j) -> board.getSymbol(i, j))));
        ruleMap.get(key).add(new Rule<>(board -> outerTraversals((i, j) -> board.getSymbol(j, i))));
        ruleMap.get(key).add(new Rule<>(board -> traverse(i -> board.getSymbol(i, i))));
        ruleMap.get(key).add(new Rule<>(board -> traverse(i -> board.getSymbol(i, 2 - i))));
        ruleMap.get(key).add(new Rule<>(board -> {
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
    }
    public GameInfo getInfo(Board board) {
        if(board instanceof TicTacToeBoard) {
            GameState gameState = getState(board);
            String[] players = {"X", "O"};
            for(int index = 0; index < 2; index++) {
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 3; j++) {
                        Board b = board.copy();
                        Player player = new Player(players[index]);
                        b.move(new Move(new Cell(i, j), player));
                        boolean canStillWin = false;
                        for(int k = 0; k < 3; k++) {
                            for(int l = 0; l < 3; l++) {
                                Board b1 = board.copy();
                                b1.move(new Move(new Cell(k, l), player.flip()));
                                if(!getState(b1).getWinner().equals(player.flip().symbol())) {
                                    canStillWin = true;
                                    break;
                                }
                            }
                            if(canStillWin) {
                                break;
                            }
                        }
                        if(canStillWin) {
                            return new GameInfoBuilder()
                                    .isOver(gameState.isOver())
                                    .winner(gameState.getWinner())
                                    .hasFork(true)
                                    .player(player.flip())
                                    .build();
                        }
                    }
                }
            }
            return new GameInfoBuilder()
                    .isOver(gameState.isOver())
                    .winner(gameState.getWinner())
                    .build();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public GameState getState(Board board) {
        if(board instanceof TicTacToeBoard) {
            TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;
            for(Rule<TicTacToeBoard> rule : ruleMap.get(TicTacToeBoard.class.getName())) {
                GameState gameState = rule.condition.apply(ticTacToeBoard);
                if(gameState.isOver()) {
                    return gameState;
                }
            }
            return new GameState(false, "-");
        } else {
            throw new IllegalArgumentException();
        }
    }

    private GameState findDiagStreak(Function<Integer, String> traversal) {
        return traverse(traversal);
    }

    private GameState outerTraversals(BiFunction<Integer, Integer, String> next) {
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


class Rule <T extends Board> {
    Function<T, GameState> condition;

    public Rule(Function<T, GameState> condition) {
        this.condition = condition;
    }
}

