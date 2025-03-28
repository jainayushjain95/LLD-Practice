package api;

import boards.TicTacToeBoard;
import game.*;

public class GameEngine {

    public Board start(String type) throws IllegalAccessException {
        if(type.equals("TicTacToe")) {
            return new TicTacToeBoard();
        } else {
            throw new IllegalAccessException();
        }
    }

    public void move(Board board, Player player, Move move) throws IllegalAccessException {
        if(board instanceof TicTacToeBoard) {
            TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;
            ticTacToeBoard.setCell(move.getCell(), player.symbol());
        } else {
            throw new IllegalAccessException();
        }
    }

    public GameResult isComplete(Board board) {
        if(board instanceof TicTacToeBoard) {
            TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;
            String firstCharacter = "-";
            boolean rowComplete = true;
            for(int i = 0; i < 3; i++) {
                firstCharacter = ticTacToeBoard.getCell(i, 0);
                if(firstCharacter.equals("-")) {
                    rowComplete = false;
                } else {
                    rowComplete = true;
                    for(int j = 1; j < 3; j++) {
                        if(!ticTacToeBoard.getCell(i, j).equals(firstCharacter)) {
                            rowComplete = false;
                            break;
                        }
                    }
                }

                if(rowComplete) {
                    return new GameResult(true, firstCharacter);
                }
            }

            boolean colComplete = true;
            for(int i = 0; i < 3; i++) {
                firstCharacter = ticTacToeBoard.getCell(0, i);
                if(firstCharacter.equals("-")) {
                    colComplete = false;
                } else {
                    colComplete = true;
                    for(int j = 1; j < 3; j++) {
                        if(!ticTacToeBoard.getCell(j, i).equals(firstCharacter)) {
                            colComplete = false;
                            break;
                        }
                    }
                }
                if(colComplete) {
                    return new GameResult(true, firstCharacter);
                }
            }

            firstCharacter = ticTacToeBoard.getCell(0, 0);
            boolean diagComplete = true;
            if(firstCharacter.equals("-")) {
                diagComplete = false;
            } else {
                for(int i = 0; i < 3; i++) {
                    if(!ticTacToeBoard.getCell(i, i).equals(firstCharacter)) {
                        diagComplete = false;
                        break;
                    }
                }
            }

            if(diagComplete) {
                return new GameResult(true, firstCharacter);
            }

            firstCharacter = ticTacToeBoard.getCell(0, 2);
            boolean reverseDiagComplete = true ;

            if(firstCharacter.equals("-")) {
                reverseDiagComplete = false;
            } else {
                for(int i = 0; i < 3; i++) {
                    if(!firstCharacter.equals(ticTacToeBoard.getCell(i, 2 - i))) {
                        reverseDiagComplete = false;
                        break;
                    }
                }
            }

            if(reverseDiagComplete) {
                return new GameResult(true, firstCharacter);
            }

            int countOfFilledCells = 0;
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(!ticTacToeBoard.getCell(i, j).equals("-")) {
                        countOfFilledCells++;
                    }
                }
            }

            if(countOfFilledCells == 9) {
                return new GameResult(true, "-");
            } else {
                return new GameResult(false, "-");
            }
        } else {
            return new GameResult(false, "-");
        }
    }

    public Move suggestMove(Player computer, Board board) throws IllegalAccessException {
        if(board instanceof TicTacToeBoard) {
            TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(ticTacToeBoard.getCell(i, j).equals("-")) {
                        return new Move(new Cell(i, j));
                    }
                }
            }
            throw new IllegalAccessException();
        } else {
            throw new IllegalAccessException();
        }
    }
}


