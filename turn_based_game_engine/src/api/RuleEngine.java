package api;

import boards.TicTacToeBoard;
import game.Board;
import game.GameState;

public class RuleEngine {

    public GameState getState(Board board) {
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
                    return new GameState(true, firstCharacter);
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
                    return new GameState(true, firstCharacter);
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
                return new GameState(true, firstCharacter);
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
                return new GameState(true, firstCharacter);
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
                return new GameState(true, "-");
            } else {
                return new GameState(false, "-");
            }
        } else {
            return new GameState(false, "-");
        }
    }

}
