package src.main.jogo.models;

public class Player {
    private final String playerId;
    private final String playerName;

    public Player(String playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }

    public String getPlayerId() {
        return playerId;
    }
    public String getPlayerName() {
        return playerName;
    }
}
