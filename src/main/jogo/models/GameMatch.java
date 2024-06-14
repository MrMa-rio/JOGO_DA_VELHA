package src.main.jogo.models;

import java.io.Serializable;
import java.util.ArrayList;

public class GameMatch implements Serializable {
    GameRoom gameRoom;
    ArrayList<Player> listPlayers;
    public GameMatch(){
        this.gameRoom = new GameRoom();
        this.listPlayers = new ArrayList<>();
    }
    public GameRoom getGameRoom() {
        return gameRoom;
    }
    public void setGameRoom(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }
    public void setListPlayers(ArrayList<Player> listPlayers) {
        this.listPlayers = listPlayers;
    }
    public ArrayList<Player> getListPlayers() {
        return listPlayers;
    }
    public void setPlayerInListPlayers(Player player) {
        listPlayers.add(player);
    }
}
