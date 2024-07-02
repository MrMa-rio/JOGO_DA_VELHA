package src.main.jogo.components.Executors;

import src.main.jogo.net.ClientHandler;
import src.main.jogo.net.packets.ClientPacket;
import src.main.jogo.net.packets.SendMessagePacket;
import src.main.jogo.services.GameClientManagerService;
import src.main.jogo.components.IExecuteSendCommand;

import java.util.ArrayList;

public class ExecuteSendMessage implements IExecuteSendCommand {

    private final GameClientManagerService gameClientManagerService;

    public ExecuteSendMessage(ClientHandler clientHandler, ArrayList<ClientHandler> clientHandlers, ClientPacket packet) {
        this.gameClientManagerService = new GameClientManagerService(clientHandlers);
        try {
            String message = ((SendMessagePacket) packet).getMessage();
            System.out.println("O player " + clientHandler.getClientId() + "\n" + "enviou a seguinte mensagem: " + message);
            gameClientManagerService.sendUpdateForOthers(clientHandler, packet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
