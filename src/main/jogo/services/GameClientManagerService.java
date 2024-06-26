package src.main.jogo.services;

import src.main.jogo.net.ClientHandler;
import src.main.jogo.net.Server;
import src.main.jogo.net.packets.ClientPacket;
import java.util.Objects;

public class GameClientManagerService {
    private final Server server;
    private final GameManagerService gameManagerService;

    public GameClientManagerService(Server server, GameManagerService gameManagerService){
        this.server = server;
        this.gameManagerService = gameManagerService;
    }
    public void teste(){

    }

    public void sendUpdatesToAll(ClientPacket packet) {
        for (final ClientHandler clientHandler : server.getClientHandlers()) {
            clientHandler.sendPacket(packet);
        }
    }
    public void sendUpdates(final ClientHandler clientHandler, ClientPacket packet) {
        clientHandler.sendPacket(packet);
    }
    public void sendUpdateById(String id, ClientPacket packet){
        server.getClientHandlers().forEach((clientHandler) -> {
            if(Objects.equals(clientHandler.getClientId(), id)){
                clientHandler.sendPacket(packet);
            }
        });
    }
    public void sendUpdateForOthers(ClientHandler clientHandler, ClientPacket packet){
        for (final ClientHandler otherClientHandler : server.getClientHandlers()) {
            if(clientHandler != otherClientHandler){
                sendUpdates(otherClientHandler, packet);
            }
        }
    }


}
