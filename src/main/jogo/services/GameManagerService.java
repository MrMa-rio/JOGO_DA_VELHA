package src.main.jogo.services;

import src.main.jogo.models.GameBoard;
import src.main.jogo.models.GameMatch;
import src.main.jogo.models.Player;
import src.main.jogo.models.GameRoom;
import src.main.jogo.net.ClientHandler;
import src.main.jogo.net.packets.SendGameBoardPacket;
import src.main.jogo.views.GameManagerView;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class GameManagerService {
    private final ArrayList<Player> guestPlayers;
    private final ArrayList<GameRoom> listGameRooms;
    private final ArrayList<GameMatch> listGameMatches;
    private final GameManagerView gameManagerView;
    private final GameBoardService gameBoardService;

    public GameManagerService(){
        this.guestPlayers = new ArrayList<>();
        this.listGameRooms = new ArrayList<>();
        this.listGameMatches = new ArrayList<>();
        this.gameManagerView = new GameManagerView();
        this.gameBoardService = new GameBoardService();
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
    public GameMatch getGameMatchInList(String codeRoom){
        AtomicReference<GameMatch> gameMatchAtomicReference = new AtomicReference<>();
        listGameMatches.forEach((gameMatchInList) -> {
            if (Objects.equals(gameMatchInList.getGameRoom().getCodeRoom(), codeRoom)){
                gameMatchAtomicReference.set(gameMatchInList);
            }
        });
        return gameMatchAtomicReference.get();
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

    public GameMatch handleStartingGameMatch(GameRoom gameRoom, String guestPlayerId) {
        GameMatch gameMatch = new GameMatch();
        gameMatch.setGameRoom(gameRoom);
        gameBoardService.create(gameMatch.getGameBoard());
        AtomicReference<Player> hostPlayer = getGuestPlayerById(gameRoom.getHostId());
        AtomicReference<Player> guestPlayer = getGuestPlayerById(guestPlayerId);
        gameMatch.setPlayerInListPlayers(hostPlayer.get());
        gameMatch.setPlayerInListPlayers(guestPlayer.get());
        listGameMatches.add(gameMatch);
        return gameMatch;
    }

    public GameMatch handleUpdateStateGame(String codeRoom, String position, String XO) {
        GameMatch gameMatch = getGameMatchInList(codeRoom);
        gameMatch.getGameBoard().setPosition(position, XO);
        return gameMatch;
    }

    public void handleUpdateStateGameForPlayers(GameMatch gameMatch, ArrayList<ClientHandler> clientHandlers) {
        ArrayList<Player> listPlayers = gameMatch.getListPlayers();
        listPlayers.forEach((player) -> {
            clientHandlers.forEach((clientHandler) -> {
                if(Objects.equals(clientHandler.getClientId(), player.playerId())){
                    clientHandler.sendUpdate(new SendGameBoardPacket(gameMatch.getGameBoard()));
                }

            });
        });

    }
    //Apos corrigir, comecar a logica de que cada player tera sua vez de jogar.


}