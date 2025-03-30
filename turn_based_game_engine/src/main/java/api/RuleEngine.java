package api;


import boards.TicTacToeBoard;
import game.*;

import java.util.HashMap;
import java.util.Map;

public class RuleEngine {

    Map<String, RuleSet<TicTacToeBoard>> ruleMap = new HashMap<>();

    public RuleEngine() {
        ruleMap.put(TicTacToeBoard.class.getName(), TicTacToeBoard.getRules());
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
                GameState gameState = rule.getCondition().apply(ticTacToeBoard);
                if(gameState.isOver()) {
                    return gameState;
                }
            }
            return new GameState(false, "-");
        } else {
            throw new IllegalArgumentException();
        }
    }
}
