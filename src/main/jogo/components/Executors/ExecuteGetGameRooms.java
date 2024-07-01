package src.main.jogo.components.Executors;

import src.main.jogo.net.ClientHandler;
import src.main.jogo.net.packets.SendGetGameRoomsPacket;
import src.main.jogo.services.GameClientManagerService;
import src.main.jogo.services.GameManagerService;
import src.main.jogo.components.IExecuteSendCommand;

import java.util.ArrayList;

public class ExecuteGetGameRooms implements IExecuteSendCommand {
    GameClientManagerService gameClientManagerService;
    public ExecuteGetGameRooms(ClientHandler clientHandler, ArrayList<ClientHandler> clientHandlers, GameManagerService gameManagerService) {
        this.gameClientManagerService = new GameClientManagerService(clientHandlers);
        gameClientManagerService.sendUpdates(clientHandler, new SendGetGameRoomsPacket(gameManagerService.getListGameRooms()));
    }
}
