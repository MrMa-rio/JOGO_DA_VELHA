package src.main.jogo.components.Executors;

import src.main.jogo.models.GameRoom;
import src.main.jogo.net.ClientHandler;
import src.main.jogo.net.packets.ClientPacket;
import src.main.jogo.net.packets.SendCreateRoomPacket;
import src.main.jogo.services.GameClientManagerService;
import src.main.jogo.services.GameManagerService;
import src.main.jogo.components.IExecuteSendCommand;
import src.main.jogo.views.GameManagerView;

import java.util.ArrayList;

public class ExecuteSendCreateRoom implements IExecuteSendCommand {
    private final GameManagerView gameManagerView;
    private final GameClientManagerService gameClientManagerService;

    public ExecuteSendCreateRoom(ClientHandler clientHandler, ArrayList<ClientHandler> clientHandlers, ClientPacket packet, GameManagerService gameManagerService) {
        this.gameClientManagerService = new GameClientManagerService(clientHandlers);
        this.gameManagerView = new GameManagerView();
        try {
            GameRoom gameRoom = ((SendCreateRoomPacket) packet).getGameRoom();
            gameManagerView.showMessage(clientHandler.getClientId(), gameRoom.getCodeRoom());
            gameManagerService.handleCreateRoom(gameRoom);
            gameClientManagerService.sendUpdateForOthers(clientHandler, packet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
