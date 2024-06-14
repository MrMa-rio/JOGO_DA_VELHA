package src.main.jogo.services;

import src.main.jogo.models.GameBoard;
import src.main.jogo.models.GameMatch;
import src.main.jogo.models.Player;
import src.main.jogo.models.GameRoom;
import src.main.jogo.views.GameManagerView;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class GameManagerService {
    private final ArrayList<Player> guestPlayers;
    private final ArrayList<GameRoom> listGameRooms;
    private final ArrayList<GameMatch> listGameMatches;
    private final GameBoardService gameBoardService;
    private final GameManagerView gameManagerView;
    private final GameBoard gameBoard;


    public GameManagerService(){
        this.guestPlayers = new ArrayList<>();
        this.listGameRooms = new ArrayList<>();
        this.listGameMatches = new ArrayList<>();
        this.gameBoardService = new GameBoardService();
        this.gameManagerView = new GameManagerView();
        this.gameBoard = new GameBoard();

    }
    public void setGameRoomsInList(GameRoom gameRoom) {
        this.listGameRooms.add(gameRoom);
    }
    public void setGuestPlayersInList(Player player) {
        this.guestPlayers.add(player);
    }
    public AtomicReference<Player> getGuestPlayerById(String guestPlayerId){
        AtomicReference<Player> guestPlayer = new AtomicReference<>();
        guestPlayers.forEach((player) -> {
            if(player.playerId().equals(guestPlayerId)){
                guestPlayer.set(player);
            }
        } );
        return guestPlayer;
    }
    public ArrayList<GameRoom> getListGameRooms() {
        return listGameRooms;
    }
    public void showListGameRooms(){
        gameManagerView.showListGameRooms(listGameRooms);
    }
    public void showListGuestPlayers(){
        gameManagerView.showListGuestPlayersConnected(guestPlayers);
    }
    public void handleCreateRoom(GameRoom gameRoom) {
        setGameRoomsInList(gameRoom);
        showListGameRooms();
    }
    public GameRoom existRoom(String codeRoom){
        AtomicReference<GameRoom> gameRoom = new AtomicReference<>();
        if(listGameRooms.isEmpty()) return gameRoom.get();
        listGameRooms.forEach((room) -> {
            if(room.getCodeRoom().equals(codeRoom)){
                gameRoom.set(room);
            }
        });
        return gameRoom.get();
    }

    public GameMatch handleStartingGameMatch(GameRoom gameRoom, Player guestplayer) {
        GameMatch gameMatch = new GameMatch();
        gameMatch.setGameRoom(gameRoom);
        AtomicReference<Player> hostPlayer = getGuestPlayerById(gameRoom.getHostId());
        gameMatch.setPlayerInListPlayers(hostPlayer.get());
        gameMatch.setPlayerInListPlayers(guestplayer);
        return gameMatch;
    }
}