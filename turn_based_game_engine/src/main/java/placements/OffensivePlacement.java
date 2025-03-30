package placements;

import api.RuleEngine;
import boards.TicTacToeBoard;
import game.Cell;
import game.Move;
import game.Player;
import utils.Utils;

import java.util.Optional;

public class OffensivePlacement implements Placement {

    private static OffensivePlacement offensivePlacement;

    private OffensivePlacement() {}

    public synchronized static Placement get() {
        offensivePlacement = (OffensivePlacement) Utils.getIfNull(offensivePlacement, () -> new OffensivePlacement());
        return offensivePlacement;
    }

    @Override
    public Optional<Cell> place(TicTacToeBoard board, Player player) {
        return Optional.ofNullable(offense(player, board));
    }

    @Override
    public Placement next() {
        return DefensivePlacement.get();
    }


    private Cell offense(Player player, TicTacToeBoard ticTacToeBoard) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(ticTacToeBoard.getSymbol(i, j).equals("-")) {
                    Move move = new Move(new Cell(i, j), player);
                    TicTacToeBoard boardCopy = ticTacToeBoard.copy();
                    boardCopy.move(move);
                    if(ruleEngine.getState(boardCopy).isOver()) {
                        return move.getCell();
                    }
                }
            }
        }
        return null;
    }
}
