package api;


import boards.TicTacToeBoard;
import game.*;
import placements.OffensivePlacement;
import placements.Placement;

import java.util.Optional;

public class AIEngine {

    RuleEngine ruleEngine = new RuleEngine();

    public Move suggestMove(Player player, Board board) {
        RuleEngine ruleEngine = new RuleEngine();
        if(board instanceof TicTacToeBoard) {
            TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;
            Cell suggestion;
            int threshold = 3;
            if(countMoves(ticTacToeBoard) < threshold) {
                suggestion = getBasicMove(ticTacToeBoard);
            } else if(countMoves(ticTacToeBoard) < threshold + 1) {
                suggestion = getCellToPlay(player, ticTacToeBoard);
            } else {
                suggestion = getOptimalMove(player, ticTacToeBoard);
            }
            if (suggestion != null) {
                return new Move(suggestion, player);
            }
            throw new IllegalStateException();
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Cell getOptimalMove(Player player, TicTacToeBoard ticTacToeBoard) {
        Placement placement = OffensivePlacement.get();
        while (placement.next() != null) {
            Optional<Cell> place = placement.place(ticTacToeBoard, player);
            if(place.isPresent()) {
                return place.get();
            }
            placement = placement.next();
        }
        return null;
    }

    private Cell getCellToPlay(Player player, TicTacToeBoard ticTacToeBoard) {

        Cell cell = offense(player, ticTacToeBoard);
        if (cell != null) {
            return cell;
        }

        cell = defense(player, ticTacToeBoard);
        if (cell != null) {
            return cell;
        }

        return getBasicMove(ticTacToeBoard);
    }

    private Cell offense(Player player, TicTacToeBoard ticTacToeBoard) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(ticTacToeBoard.getSymbol(i, j).equals("-")) {
                    Move move = new Move(new Cell(i, j), player);
                    TicTacToeBoard boardCopy = ticTacToeBoard.move(move);
                    if(ruleEngine.getState(boardCopy).isOver()) {
                        return move.getCell();
                    }
                }
            }
        }
        return null;
    }

    private Cell defense(Player player, TicTacToeBoard ticTacToeBoard) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(ticTacToeBoard.getSymbol(i, j).equals("-")) {
                    Move move = new Move(new Cell(i, j), player.flip());
                    TicTacToeBoard boardCopy = ticTacToeBoard.move(move);
                    if(ruleEngine.getState(boardCopy).isOver()) {
                        return new Cell(i, j);
                    }
                }
            }
        }
        return null;
    }


    private int countMoves(TicTacToeBoard board) {
        int count = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(!board.getSymbol(i, j).equals("-")) {
                    count++;
                }
            }
        }
        return count;
    }

    private Cell getBasicMove(TicTacToeBoard ticTacToeBoard) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(ticTacToeBoard.getSymbol(i, j).equals("-")) {
                    return new Cell(i, j);
                }
            }
        }
        return null;
    }

}
