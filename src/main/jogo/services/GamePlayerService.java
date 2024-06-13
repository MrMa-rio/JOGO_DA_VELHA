package src.main.jogo.services;

import src.main.jogo.models.GameRoom;


public class GamePlayerService {
    private final GameModeOnlineService gameModeOnlineService;
    public GamePlayerService(){
        this.gameModeOnlineService = new GameModeOnlineService();
    }
    public void handleGameMatch(){

    }
    public void handleGameRoomExist(GameRoom gameRoom) {
        if(gameRoom == null){
            System.out.println("Codigo de sala invalido!");
            gameModeOnlineService.enterRoom();
            return;
        }
        gameModeOnlineService.setGameRoom(gameRoom.getCodeRoom(), gameRoom.getHostId());
        gameModeOnlineService.teste();
    }
}
