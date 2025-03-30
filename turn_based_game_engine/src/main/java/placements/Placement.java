package placements;

import api.RuleEngine;
import boards.TicTacToeBoard;
import game.Board;
import game.Cell;
import game.Player;

import java.util.Optional;

public interface Placement {

    RuleEngine ruleEngine = new RuleEngine();

    public Optional<Cell> place(TicTacToeBoard board, Player player);
    Placement next();

}
