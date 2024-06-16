package src.main.jogo.services;

import src.main.jogo.models.GameRoom;
import src.main.jogo.models.Player;
import src.main.jogo.net.Client;
import src.main.jogo.net.packets.SendCreateRoomPacket;
import src.main.jogo.net.packets.SendEnterRoomPacket;
import src.main.jogo.net.packets.SendPlayerPacket;
import src.main.jogo.utils.RandomStringGenerator;
import src.main.jogo.views.GameModeOnlineView;

public class GameModeOnlineService {
    private final GameModeOnlineView gameModeOnlineView;
    GameRoom gameRoom = new GameRoom();
    private static Client client;
    private Player player;

    public GameModeOnlineService(){
        this.gameModeOnlineView = new GameModeOnlineView();
    }
    public void initializeClient(String ipAdress, int port){
        client = new Client(ipAdress, port);
        client.run();
    }

    public static Client getClient() {
        return client;
    }

    public void createPlayer(){
        String playerName = gameModeOnlineView.setPlayerName();
        String playerId = client.getClientId();
        this.player = new Player(playerId, playerName);
        client.sendPacket(new SendPlayerPacket(player));
    }
    public void setGameRoom(String codeRoom, String hostId){
        this.gameRoom.setCodeRoom(codeRoom);
        this.gameRoom.setHostId(hostId);
    }

    public void createRoom(){
        setGameRoom(RandomStringGenerator.random(), client.getClientId());
        client.sendPacket(new SendCreateRoomPacket(gameRoom));
        System.out.println("Aguardando jogador se conectar nessa sala...");
    }
    public void enterRoom(){
        String codeRoom = gameModeOnlineView.setCodeRoom();
        client.sendPacket(new SendEnterRoomPacket(codeRoom));
    }
}
