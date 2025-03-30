package placements;

import boards.TicTacToeBoard;
import game.Board;
import game.Cell;
import game.Player;
import utils.Utils;

import java.util.Optional;

public class CenterPlacement implements Placement {

    private static CenterPlacement centerPlacement;

    private CenterPlacement() {}

    public synchronized static Placement get() {
        centerPlacement = (CenterPlacement) Utils.getIfNull(centerPlacement, () -> new CenterPlacement());
        return centerPlacement;
    }

    @Override
    public Optional<Cell> place(TicTacToeBoard board, Player player) {
        return Optional.ofNullable(placeInCenter(board));
    }

    private static Cell placeInCenter(TicTacToeBoard board) {
        Cell center = null;
        if(board.getSymbol(1, 1).equals("-")) {
            center = new Cell(1, 1);
        }
        return center;
    }


    @Override
    public Placement next() {
        return CornerPlacement.get();
    }
}
