package game;


public class GameInfo {

    private boolean isOver;
    private String winner;
    private boolean hasFork;
    private Player player;
    private int numberOfMoves;
    private Cell forkCell;

    public GameInfo(boolean isOver, String winner, boolean hasFork, Player player, int numberOfMoves, Cell forkCell) {
        this.hasFork = hasFork;
        this.player = player;
        this.isOver = isOver;
        this.winner = winner;
        this.numberOfMoves = numberOfMoves;
        this.forkCell = forkCell;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public boolean hasAFork() {
        return hasFork;
    }

    public void setHasFork(boolean hasFork) {
        this.hasFork = hasFork;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }

    public Cell getForkCell() {
        return forkCell;
    }

    public void setForkCell(Cell forkCell) {
        this.forkCell = forkCell;
    }
}