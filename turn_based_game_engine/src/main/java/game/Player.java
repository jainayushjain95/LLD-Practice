package game;

public class Player {

    private User user;
    private long timeUsedInMillis;
    private String playerSymbol;

    public Player(String playerSymbol) {
        this.playerSymbol = playerSymbol;
    }

    public String symbol() {
        return playerSymbol;
    }

    public Player flip() {
        return new Player(playerSymbol.equals("X") ? "O" : "X");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTimeUsedInMillis() {
        return timeUsedInMillis;
    }

    public void setTimeUsedInMillis(long timeUsedInMillis) {
        this.timeUsedInMillis += timeUsedInMillis;
    }

    public String getPlayerSymbol() {
        return playerSymbol;
    }

    public void setPlayerSymbol(String playerSymbol) {
        this.playerSymbol = playerSymbol;
    }
}
