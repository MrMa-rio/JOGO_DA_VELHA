package src.main.jogo.services;

import src.main.jogo.net.ClientHandler;
import src.main.jogo.net.packets.ClientPacket;

import java.util.List;
import java.util.Objects;

public class GameClientManagerService {
    private final List<ClientHandler> clientHandlers;
    public GameClientManagerService(List<ClientHandler> clientHandlers){
        this.clientHandlers = clientHandlers;
    }
    public void sendUpdatesToAll(ClientPacket packet) {
        for (final ClientHandler clientHandler : clientHandlers) {
            clientHandler.sendPacket(packet);
        }
    }
    public void sendUpdates(final ClientHandler clientHandler, ClientPacket packet) {
        clientHandler.sendPacket(packet);
    }
    public void sendUpdateById(String id, ClientPacket packet){
        clientHandlers.forEach((clientHandler) -> {
            if(Objects.equals(clientHandler.getClientId(), id)){
                clientHandler.sendPacket(packet);
            }
        });
    }
    public void sendUpdateForOthers(ClientHandler clientHandler, ClientPacket packet){
        for (final ClientHandler otherClientHandler : clientHandlers) {
            if(clientHandler != otherClientHandler){
                sendUpdates(otherClientHandler, packet);
            }
        }
    }
}
