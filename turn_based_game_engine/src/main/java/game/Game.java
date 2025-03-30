package game;

public class Game {
    private GameConfig gameConfig;
    private Board board;
    Player winner;
    private long lastMoveTimeInMillis;
    private long maxTimePerPlayer;
    private long maxTimePerMove;

    public void move(Move move, long timestampInMills) {
        long timeTakeSinceLastMoveInMillis = timestampInMills - lastMoveTimeInMillis;;
        move.getPlayer().setTimeUsedInMillis(timeTakeSinceLastMoveInMillis);

        if(gameConfig.timed) {
            moveForTimedGame(move, timeTakeSinceLastMoveInMillis);
        } else {
            board.move(move);
        }
    }

    private void moveForTimedGame(Move move, long timeTakeSinceLastMoveInMillis) {
        if(gameConfig.timePerMove != null) {
            if(moveMadeInTime(timeTakeSinceLastMoveInMillis)) {
                board.move(move);
            } else {
                winner = move.getPlayer().flip();
            }
        } else {
            if(moveMadeInTime(move.getPlayer())) {
                board.move(move);
            } else {
                winner = move.getPlayer().flip();
            }
        }
    }

    private boolean moveMadeInTime(long timeTakeSinceLastMoveInMillis) {
        return timeTakeSinceLastMoveInMillis < maxTimePerMove;
    }

    private boolean moveMadeInTime(Player player) {
        return player.getTimeUsedInMillis() < maxTimePerPlayer;
    }
}
