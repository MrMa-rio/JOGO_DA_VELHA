package src.main.jogo.services;

import src.main.jogo.models.*;
import src.main.jogo.net.packets.SendStartedGameMatchPacket;
import src.main.jogo.net.packets.SendStateGameBoardPacket;

public class GamePlayerService {
    private final GameModeOnlineService gameModeOnlineService;
    private GameMatchService gameMatchService;
    public GamePlayerService(){
       this.gameModeOnlineService = new GameModeOnlineService();

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

    public void handleChoicePosition() {
       gameMatchService.handleChoicePosition();
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

    public void handleShowBoardState(GameBoard gameBoard) {
        gameMatchService.getGameMatch().getGameBoard().setGameBoard(gameBoard.getGameBoard());
        gameMatchService.handleShowBoardState(gameBoard);
    }
}
