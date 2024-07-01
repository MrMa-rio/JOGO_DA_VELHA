package src.main.jogo.components.Executors;

import src.main.jogo.net.ClientHandler;
import src.main.jogo.net.packets.ClientPacket;
import src.main.jogo.net.packets.SendCloseGameRoomPacket;
import src.main.jogo.net.packets.SendQuitGameMatchPacket;
import src.main.jogo.services.GameClientManagerService;
import src.main.jogo.services.GameManagerService;
import src.main.jogo.components.IExecuteSendCommand;

import java.util.ArrayList;

public class ExecuteSendCloseGameRoom implements IExecuteSendCommand {

    GameClientManagerService gameClientManagerService;

    public ExecuteSendCloseGameRoom(ClientHandler clientHandler, ArrayList<ClientHandler> clientHandlers, ClientPacket packet, GameManagerService gameManagerService) {
        this.gameClientManagerService = new GameClientManagerService(clientHandlers);
        gameManagerService.handleClosingGameRoom(((SendCloseGameRoomPacket) packet).getCodeRoom());
        System.out.println("FECHANDO SALA DA PARTIDA...");
        gameClientManagerService.sendUpdates(clientHandler, new SendQuitGameMatchPacket());

    }
}
