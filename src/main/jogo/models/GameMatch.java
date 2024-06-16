package src.main.jogo.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class GameMatch implements Serializable {
    private GameRoom gameRoom;
    private GameBoard gameBoard;
    private boolean isStart;
    ArrayList<Player> listPlayers;
    public GameMatch(){
        this.gameRoom = new GameRoom();
        this.listPlayers = new ArrayList<>();
        this.gameBoard = new GameBoard();
    }

    public void setStart(boolean start) {
        isStart = start;
    }
    public boolean getIsStart(){
        return isStart;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
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
    public String getNextPlayer(String playerId){
        AtomicReference<String> nextPlayer = new AtomicReference<>();
        getListPlayers().forEach((player) -> {
            if(!Objects.equals(player.playerId(), playerId)) nextPlayer.set(player.playerId());
        });
        return nextPlayer.get();
    }
    public void setPlayerInListPlayers(Player player) {
        listPlayers.add(player);
    }
}
