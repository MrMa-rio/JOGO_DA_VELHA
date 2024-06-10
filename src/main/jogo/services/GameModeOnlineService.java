package src.main.jogo.services;

import src.main.jogo.models.GameRoom;
import src.main.jogo.net.Client;
import src.main.jogo.views.GameModeOnlineView;

public class GameModeOnlineService {
    GameManagerService gameManagerService = new GameManagerService();
    GameModeOnlineView gameModeOnlineView = new GameModeOnlineView();
    GameRoom gameRoom = new GameRoom();

    public GameModeOnlineService(){

    }
    public void createRoom(){
        String codeRoom;
        codeRoom = gameModeOnlineView.setCodeRoom();
        gameRoom.setCodeRoom(codeRoom);

        System.out.println("Aguardando jogador se conectar nessa sala...");
        new Client("172.16.232.203", 1234).run();

    }
    public void enterRoom(){
        new Client("172.16.232.203", 1234).run();
    }
}
