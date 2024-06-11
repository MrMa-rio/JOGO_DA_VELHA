package src.main.jogo.models;

import java.util.ArrayList;

public class GameMatch {
    GameRoom gameRoom;
    ArrayList<Player> listPlayers;
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
