

import api.AIEngine;
import api.GameEngine;
import api.RuleEngine;
import game.Board;
import game.Cell;
import game.Move;
import game.Player;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IllegalAccessException {
        GameEngine gameEngine = new GameEngine();
        AIEngine aiEngine = new AIEngine();
        RuleEngine ruleEngine = new RuleEngine();

        Board board = gameEngine.start("TicTacToe");
        int row, col;
        Scanner scanner = new Scanner(System.in);

        Player computer = new Player("O");
        Player opponent = new Player("X");

        while(!ruleEngine.getState(board).isOver()) {
            System.out.println("\nMake Your Move!");
            System.out.print(board);
            row = scanner.nextInt();
            col = scanner.nextInt();

            Move opponentMove = new Move(new Cell(row, col), opponent);
            gameEngine.move(board, opponentMove);
            System.out.print(board);
            if(!ruleEngine.getState(board).isOver()) {
                System.out.println("Computer Making a Move!");
                Move computerMove = aiEngine.suggestMove(computer, board);
                gameEngine.move(board, computerMove);
                System.out.print(board + "\n");
            }
        }

        System.out.println("Game Result: " + ruleEngine.getState(board));
        System.out.print(board);
    }

}
