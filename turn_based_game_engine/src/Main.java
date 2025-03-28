import api.GameEngine;
import game.Board;
import game.Cell;
import game.Move;
import game.Player;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IllegalAccessException {
        GameEngine gameEngine = new GameEngine();
        Board board = gameEngine.start("TicTacToe");
        int row, col;
        Scanner scanner = new Scanner(System.in);

        Player computer = new Player("O");
        Player opponent = new Player("X");

        while(!gameEngine.isComplete(board).isOver()) {
            System.out.println("\nMake Your Move!");
            System.out.print(board);
            row = scanner.nextInt();
            col = scanner.nextInt();

            Move opponentMove = new Move(new Cell(row, col));
            gameEngine.move(board, opponent, opponentMove);
            System.out.print(board);
            if(!gameEngine.isComplete(board).isOver()) {
                System.out.println("Computer Making a Move!");
                Move computerMove = gameEngine.suggestMove(computer, board);
                gameEngine.move(board, computer, computerMove);
                System.out.print(board + "\n");
            }
        }

        System.out.println("Game Result: " + gameEngine.isComplete(board));
        System.out.print(board);
    }

}
