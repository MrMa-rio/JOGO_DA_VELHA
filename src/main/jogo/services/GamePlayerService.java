package src.main.jogo.services;

import src.main.jogo.models.*;
import src.main.jogo.net.packets.SendEnterRoomPacket;
import src.main.jogo.net.packets.SendGetGameRoomsPacket;
import src.main.jogo.net.packets.SendStartedGameMatchPacket;
import src.main.jogo.net.packets.SendStateGameBoardPacket;
import src.main.jogo.views.GameMatchView;
import src.main.jogo.views.GameModeOnlineView;

import java.util.ArrayList;

public class GamePlayerService {
    private final GameModeOnlineService gameModeOnlineService;
    private GameMatchService gameMatchService;
    private final GameModeOnlineView gameModeOnlineView;
    private final GameMatchView gameMatchView;

    public GamePlayerService(){
        this.gameModeOnlineService = new GameModeOnlineService();
        this.gameModeOnlineView = new GameModeOnlineView();
        this.gameMatchView = new GameMatchView();
    }
    public void handleGameRoomExist(GameRoom gameRoom) {
        if(gameRoom == null){
            System.out.println("Codigo de sala invalido!");
            gameModeOnlineService.enterRoom();
            return;
        }
        gameModeOnlineService.setGameRoom(gameRoom.getCodeRoom(), gameRoom.getHostId());
    }
    public void handleStartingMatching(GameMatch gameMatch) {
        gameMatchService = new GameMatchService(gameMatch);
        gameMatchService.startingGameBoard();
        GameModeOnlineService.getClient().sendPacket(new SendStartedGameMatchPacket(gameMatch));
    }

    public void handleMovePlayer(String XO, String hostXO) {
        String codeRoom = gameMatchService.getGameMatch().getGameRoom().getCodeRoom();
        String playerId = GameModeOnlineService.getClient().getClientId();
        PlayerInMatch player = gameMatchService.getGameMatch().getPlayerInListPlayersById(playerId);
        if (XO.isEmpty()){
            player.setXO(gameMatchService.handleChoiceXO(hostXO));
        }
        String position = gameMatchService.handleChoicePosition();
        GameModeOnlineService.getClient().sendPacket(new SendStateGameBoardPacket(player, codeRoom, position, player.getXO()));
    }

    public void handleStartingPlayer(){
        String ipAdress;
        int port;
        do{
            ipAdress = gameModeOnlineView.setIpAdress();
            port = gameModeOnlineView.setPort();
        } while(!gameModeOnlineService.initializeClient(ipAdress, port));
        gameModeOnlineService.createPlayer();
    }
    public void handleCreatingRoom(){
        gameModeOnlineService.createRoom();
    }
    public void handleEnteringRoom(){
        gameModeOnlineService.enterRoom();
    }

    public void handleShowBoardState(GameBoard gameBoard) {
        gameMatchService.getGameMatch().getGameBoard().setGameBoard(gameBoard.getGameBoard());
        gameMatchService.handleShowBoardState(gameBoard);
    }

    public void handleGetGameRooms() {
        GameModeOnlineService.getClient().sendPacket(new SendGetGameRoomsPacket());
    }
    public void handleShowGameRooms(ArrayList<GameRoom> gameRooms){
        gameMatchView.showListGameRooms(gameRooms);
        String codeRoom;
        do{
            codeRoom = gameMatchView.choiceGameMatch(gameRooms);
        }
        while (codeRoom == null);
        GameModeOnlineService.getClient().sendPacket(new SendEnterRoomPacket(codeRoom));
    }
}
