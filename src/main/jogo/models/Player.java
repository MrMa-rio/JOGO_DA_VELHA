package src.main.jogo.models;

import java.io.Serializable;

public class Player implements Serializable {
    private final String playerId, playerName;

    public Player(String playerId, String playerName){
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
