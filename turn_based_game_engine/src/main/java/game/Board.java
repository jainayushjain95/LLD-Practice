package game;

import boards.TicTacToeBoard;

public interface Board {

    void move(Move move);

    Board copy();
}

