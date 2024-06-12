package src.main.jogo.services;

import src.main.jogo.models.GameRoom;


public class GamePlayerService {
    GameModeOnlineService gameModeOnlineService = new GameModeOnlineService();
    public void handleGameMatch(){

    }
    public void handleGameRoomExist(GameRoom gameRoom) {
        if(gameRoom == null){
            System.out.println("Codigo de sala invalido!");
            gameModeOnlineService.enterRoom();
        }
    }
}
