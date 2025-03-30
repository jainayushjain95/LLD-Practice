package placements;

import api.RuleEngine;
import boards.TicTacToeBoard;
import game.Board;
import game.Cell;
import game.Move;
import game.Player;
import utils.Utils;

import java.util.Optional;

public class DefensivePlacement implements Placement {

    private static DefensivePlacement defensivePlacement;

    private DefensivePlacement() {}

    public synchronized static Placement get() {
        defensivePlacement = (DefensivePlacement) Utils.getIfNull(defensivePlacement, () -> new DefensivePlacement());
        return defensivePlacement;
    }

    @Override
    public Optional<Cell> place(TicTacToeBoard board, Player player) {
        return Optional.ofNullable(defense(player, board));
    }

    @Override
    public Placement next() {
        return ForkPlacement.get();
    }

    private Cell defense(Player player, TicTacToeBoard ticTacToeBoard) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(ticTacToeBoard.getSymbol(i, j).equals("-")) {
                    Move move = new Move(new Cell(i, j), player.flip());
                    TicTacToeBoard boardCopy = ticTacToeBoard.copy();
                    boardCopy.move(move);
                    if(ruleEngine.getState(boardCopy).isOver()) {
                        return new Cell(i, j);
                    }
                }
            }
        }
        return null;
    }
}
