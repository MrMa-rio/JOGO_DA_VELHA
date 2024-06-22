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
    private final GameRoom gameRoom;
    private static Client client;
    private Player player;

    public GameModeOnlineService(){

        this.gameModeOnlineView = new GameModeOnlineView();
        this.gameRoom = new GameRoom();
    }
    public boolean initializeClient(){
        try{
            client = new Client();
            client.run();
            return true;
        }catch (RuntimeException e){
            return false;
        }
    }

    public static Client getClient() {
        return client;
    }

    public GameRoom getGameRoom() {
        return gameRoom;
    }

    public void createPlayer(){
        if(player == null){
            String playerName = gameModeOnlineView.setPlayerName();
            String playerId = client.getClientId();
            this.player = new Player(playerId, playerName);
            client.sendPacket(new SendPlayerPacket(player));
        }
    }
    public void setGameRoom(String codeRoom, String hostId){
        this.gameRoom.setCodeRoom(codeRoom);
        this.gameRoom.setHostId(hostId);
    }

    public void createRoom(){
        setGameRoom(RandomStringGenerator.random(), client.getClientId());
        client.sendPacket(new SendCreateRoomPacket(gameRoom));
        System.out.println("O codigo da sala e: " + gameRoom.getCodeRoom());
        System.out.println("Aguardando jogador se conectar nessa sala...");
    }
    public void enterRoom(){
        String codeRoom = gameModeOnlineView.setCodeRoom();
        client.sendPacket(new SendEnterRoomPacket(codeRoom));
    }
}
