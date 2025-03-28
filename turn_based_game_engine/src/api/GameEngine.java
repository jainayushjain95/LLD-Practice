package api;

import boards.TicTacToeBoard;
import game.*;

public class GameEngine {

    public Board start() {
        return new Board();
    }

    public void move(Board board, Player player, Move move) {

    }

    public GameResult isComplete(Board board) {
        if(board instanceof TicTacToeBoard) {
            TicTacToeBoard ticTacToeBoard = (TicTacToeBoard) board;
            String firstCharacter = "-";
            boolean rowComplete = true;
            for(int i = 0; i < 3; i++) {
                rowComplete = true;
                firstCharacter = ticTacToeBoard.getCell(i, 0);
                for(int j = 1; j < 3; j++) {
                    if(!ticTacToeBoard.getCell(i, j).equals(firstCharacter)) {
                        rowComplete = false;
                        break;
                    }
                }
                if(rowComplete) {
                    return new GameResult(true, firstCharacter);
                }
            }

            boolean colComplete = true;
            for(int i = 0; i < 3; i++) {
                colComplete = true;
                firstCharacter = ticTacToeBoard.getCell(0, i);
                for(int j = 1; j < 3; j++) {
                    if(!ticTacToeBoard.getCell(j, i).equals(firstCharacter)) {
                        colComplete = false;
                        break;
                    }
                }
                if(colComplete) {
                    return new GameResult(true, firstCharacter);
                }
            }

            firstCharacter = ticTacToeBoard.getCell(0, 0);
            boolean diagComplete = true;

            for(int i = 0; i < 3; i++) {
                if(!ticTacToeBoard.getCell(i, i).equals(firstCharacter)) {
                    diagComplete = false;
                    break;
                }
            }

            if(diagComplete) {
                return new GameResult(true, firstCharacter);
            }

            firstCharacter = ticTacToeBoard.getCell(0, 2);
            boolean reverseDiagComplete = true;

            for(int i = 0; i < 3; i++) {
                if(!ticTacToeBoard.getCell(i, 2 - i).equals(firstCharacter)) {
                    reverseDiagComplete = false;
                    break;
                }
            }

            if(reverseDiagComplete) {
                return new GameResult(true, firstCharacter);
            }

            int countOfFilledCells = 0;
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(ticTacToeBoard.getCell(i, j) != null) {
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
}


