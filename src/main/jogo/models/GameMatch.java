package src.main.jogo.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class GameMatch implements Serializable {
    private GameRoom gameRoom;
    private final GameBoard gameBoard;
    private boolean isStart;
    private boolean isClosed;
    ArrayList<PlayerInMatch> listPlayers;
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
    public void setClosed(boolean closed) {
        isClosed = closed;
    }
    public boolean getIsClosed(){
        return isClosed;
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
    public ArrayList<PlayerInMatch> getListPlayers() {
        return listPlayers;
    }
    public String getNextPlayer(String playerId){
        AtomicReference<String> nextPlayer = new AtomicReference<>();
        getListPlayers().forEach((player) -> {
            if(!Objects.equals(player.getPlayer().playerId(), playerId)) nextPlayer.set(player.getPlayer().playerId());
        });
        return nextPlayer.get();
    }
    public void setPlayerInListPlayers(PlayerInMatch player) {
        listPlayers.add(player);
    }
    public PlayerInMatch getPlayerInListPlayersById(String playerId){
        AtomicReference<PlayerInMatch> playerInList = new AtomicReference<>();
        getListPlayers().forEach((player) -> {
            if(Objects.equals(player.getPlayer().playerId(), playerId)) playerInList.set(player);
        });
        return playerInList.get();
    }
}
