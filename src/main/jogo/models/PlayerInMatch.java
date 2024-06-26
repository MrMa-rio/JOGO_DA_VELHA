package src.main.jogo.models;

import java.io.Serializable;

public class PlayerInMatch extends Player {
    private String XO = "";
    public PlayerInMatch(String playerId, String playerName){
        super(playerId, playerName);
    }
    public void setXO(String XO) {
        this.XO = XO;
    }
    public String getXO() {
        return XO;
    }
}
