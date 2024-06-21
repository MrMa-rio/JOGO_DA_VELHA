package src.main.jogo.models;

import java.io.Serializable;

public class PlayerInMatch implements Serializable {
    private final Player player;
    private String XO = "";
    public PlayerInMatch(Player player){
        this.player = player;
    }
    public Player getPlayer() {
        return player;
    }
    public void setXO(String XO) {
        this.XO = XO;
    }
    public String getXO() {
        return XO;
    }
}
