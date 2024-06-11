package src.main.jogo.services;

import src.main.jogo.models.GameRoom;
import src.main.jogo.net.Client;
import src.main.jogo.net.packets.SendCreateRoomPacket;
import src.main.jogo.utils.RandomStringGenerator;
import src.main.jogo.views.GameModeOnlineView;

public class GameModeOnlineService {
    GameModeOnlineView gameModeOnlineView = new GameModeOnlineView();
    GameRoom gameRoom = new GameRoom();
    Client client;
    public GameModeOnlineService(){
    }
    public void initializeClient(){
        this.client = new Client(/*"172.16.232.203"*/"192.168.3.18", 1234);
        client.run();
    }
    public void createRoom(){

        gameRoom.setCodeRoom(RandomStringGenerator.random());
        gameRoom.setHostId(client.getClientId());
        client.sendPacket(new SendCreateRoomPacket(gameRoom));
        System.out.println("Aguardando jogador se conectar nessa sala...");
    }
    public void enterRoom(){

    }
}
