package src.main.jogo.services;

import src.main.jogo.models.*;
import src.main.jogo.net.packets.*;
import src.main.jogo.views.GameMatchView;

import java.util.ArrayList;

public class GamePlayerService {
    private final GameModeOnlineService gameModeOnlineService;
    private GameMatchService gameMatchService;
    private final GameMatchView gameMatchView;

    public GamePlayerService(){
        this.gameModeOnlineService = new GameModeOnlineService();
        this.gameMatchView = new GameMatchView();
    }
    public void handleGameRoomExist(GameRoom gameRoom) {
        if(gameRoom == null || gameRoom.getIsClosed()){
            System.out.println("Codigo de sala invalido!");
            gameModeOnlineService.enterRoom();
            return;
        }
        gameModeOnlineService.setGameRoom(gameRoom.getCodeRoom(), gameRoom.getHostId());
        System.out.println("Entrando em uma sala...");
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

        while(true){
            if(gameModeOnlineService.initializeClient()){
                break;
            }
        }
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
    public void handleClosingGameRoom(){
        GameModeOnlineService.getClient().sendPacket(new SendCloseGameRoomPacket(gameModeOnlineService.getGameRoom().getCodeRoom()));
    }
}
