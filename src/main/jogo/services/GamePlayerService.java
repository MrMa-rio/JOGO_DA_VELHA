package src.main.jogo.services;

import src.main.jogo.models.GameBoard;
import src.main.jogo.models.GameMatch;
import src.main.jogo.models.GameRoom;
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

    public void handleMovePlayer() {
        String codeRoom = gameMatchService.getGameMatch().getGameRoom().getCodeRoom();
        String XO = gameMatchService.handleChoiceXO();
        String position = gameMatchService.handleChoicePosition();
        String playerId = GameModeOnlineService.getClient().getClientId();
        //handleShowBoardState(gameMatchService.getGameMatch().getGameBoard());
        GameModeOnlineService.getClient().sendPacket(new SendStateGameBoardPacket(playerId, codeRoom, position, XO));
    }

    public void handleShowBoardState(GameBoard gameBoard) {
        gameMatchService.getGameMatch().getGameBoard().setGameBoard(gameBoard.getGameBoard());
        gameMatchService.handleShowBoardState(gameBoard);
    }
}
