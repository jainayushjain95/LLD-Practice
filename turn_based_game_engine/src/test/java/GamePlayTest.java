import api.AIEngine;
import api.GameEngine;
import api.RuleEngine;
import game.Board;
import game.Cell;
import game.Move;
import game.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class GamePlayTest {
    GameEngine gameEngine;
    RuleEngine ruleEngine;
    Player computer;
    Player opponent;

    @Before
    public void setUp() {
        gameEngine = new GameEngine();
        ruleEngine = new RuleEngine();
        computer = new Player("O");
        opponent = new Player("X");
    }

    @Test
    public void checkForRowWin() {

        Board board = gameEngine.start("TicTacToe");
        int[][] firstPlayerMoves = {{1, 0}, {1, 1}, {1, 2}};
        int[][] secondPlayerMoves = {{0, 0}, {0, 1}, {0, 2}};

        playGame(board, firstPlayerMoves, secondPlayerMoves);
        Assert.assertTrue(ruleEngine.getState(board).isOver());
        Assert.assertEquals("X", ruleEngine.getState(board).getWinner());
    }

    @Test
    public void checkForColWin() {
        Board board = gameEngine.start("TicTacToe");
        int[][] firstPlayerMoves = {{0, 2}, {1, 2}, {2, 2}};
        int[][] secondPlayerMoves = {{0, 1}, {1, 1}, {2, 1}};

        playGame(board, firstPlayerMoves, secondPlayerMoves);
        Assert.assertTrue(ruleEngine.getState(board).isOver());
        Assert.assertEquals("X", ruleEngine.getState(board).getWinner());
    }

    @Test
    public void checkForDiagonalWin() {
        Board board = gameEngine.start("TicTacToe");
        int[][] firstPlayerMoves = {{0, 0}, {1, 1}, {2, 2}};
        int[][] secondPlayerMoves = {{0, 1}, {0, 2}, {1, 0}};

        playGame(board, firstPlayerMoves, secondPlayerMoves);
        Assert.assertTrue(ruleEngine.getState(board).isOver());
        Assert.assertEquals("X", ruleEngine.getState(board).getWinner());
    }

    @Test
    public void checkForRevDiagonalWin() {
        Board board = gameEngine.start("TicTacToe");
        int[][] firstPlayerMoves = {{0, 2}, {1, 1}, {2, 0}};
        int[][] secondPlayerMoves = {{0, 0}, {0, 1}, {1, 0}};

        playGame(board, firstPlayerMoves, secondPlayerMoves);
        Assert.assertTrue(ruleEngine.getState(board).isOver());
        Assert.assertEquals("X", ruleEngine.getState(board).getWinner());
    }

    @Test
    public void checkForComputerWin() {
        Board board = gameEngine.start("TicTacToe");
        int[][] firstPlayerMoves = {{1, 0}, {1, 1}, {2, 0}};
        int[][] secondPlayerMoves = {{0, 0}, {0, 1}, {0, 2}};

        playGame(board, firstPlayerMoves, secondPlayerMoves);
        Assert.assertTrue(ruleEngine.getState(board).isOver());
        Assert.assertEquals("O", ruleEngine.getState(board).getWinner());
    }

    private void playGame(Board board, int[][] firstPlayerMoves, int[][] secondPlayerMoves) {
        int row, col, next = 0;
        while(!ruleEngine.getState(board).isOver()) {
            row = firstPlayerMoves[next][0];
            col = firstPlayerMoves[next][1];

            Move opponentMove = new Move(new Cell(row, col), opponent);
            gameEngine.move(board, opponentMove);
            if(!ruleEngine.getState(board).isOver()) {
                Move computerMove = new Move(new Cell(secondPlayerMoves[next][0], secondPlayerMoves[next][1]), computer);
                gameEngine.move(board, computerMove);
            }
            next++;
        }
    }

}
