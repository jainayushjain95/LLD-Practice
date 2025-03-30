package placements;

import boards.TicTacToeBoard;
import game.Board;
import game.Cell;
import game.Player;
import utils.Utils;

import java.util.Optional;

public class CornerPlacement implements Placement {

    private static CornerPlacement cornerPlacement;

    private CornerPlacement() {}

    public synchronized static Placement get() {
        cornerPlacement = (CornerPlacement) Utils.getIfNull(cornerPlacement, () -> new CornerPlacement());
        return cornerPlacement;
    }

    @Override
    public Optional<Cell> place(TicTacToeBoard board, Player player) {
        return Optional.ofNullable(placeInCorner(board));
    }

    private static Cell placeInCorner(TicTacToeBoard board) {
        Cell cornerCell = null;
        final int[][] corners = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
        for (int[] corner : corners) {
            if(board.getSymbol(corner[0], corner[1]).equals("-")) {
                cornerCell = new Cell(corner[0], corner[1]);
            }
        }
        return cornerCell;
    }

    @Override
    public Placement next() {
        return null;
    }
}
