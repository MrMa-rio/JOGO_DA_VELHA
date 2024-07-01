package src.main.jogo.components.Executors;

import src.main.jogo.net.ClientHandler;
import src.main.jogo.net.packets.ClientPacket;
import src.main.jogo.services.GameClientManagerService;
import src.main.jogo.components.IExecuteSendCommand;

import java.util.ArrayList;

public class ExecuteSendDisconnect implements IExecuteSendCommand {

    GameClientManagerService gameClientManagerService;

    public ExecuteSendDisconnect(ClientHandler clientHandler, ArrayList<ClientHandler> clientHandlers, ClientPacket packet) {
        this.gameClientManagerService = new GameClientManagerService(clientHandlers);
        clientHandler.disconnect();
        System.out.println("REMOVE DADOS DO JOGADOR");
        clientHandlers.remove(clientHandler);
        gameClientManagerService.sendUpdateForOthers(clientHandler, packet);

    }
}
