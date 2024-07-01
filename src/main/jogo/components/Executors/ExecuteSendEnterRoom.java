package src.main.jogo.components.Executors;

import src.main.jogo.models.GameMatch;
import src.main.jogo.models.GameRoom;
import src.main.jogo.net.ClientHandler;
import src.main.jogo.net.packets.ClientPacket;
import src.main.jogo.net.packets.SendEnterRoomPacket;
import src.main.jogo.net.packets.SendGameRoomPacket;
import src.main.jogo.net.packets.SendStartingGameMatchPacket;
import src.main.jogo.services.GameClientManagerService;
import src.main.jogo.services.GameManagerService;
import src.main.jogo.components.IExecuteSendCommand;
import src.main.jogo.views.GameManagerView;

import java.util.ArrayList;

public class ExecuteSendEnterRoom implements IExecuteSendCommand {
    GameManagerView gameManagerView;
    GameClientManagerService gameClientManagerService;
    public ExecuteSendEnterRoom(
            ClientHandler clientHandler,
            ArrayList<ClientHandler> clientHandlers,
            ClientPacket packet,
            GameManagerService gameManagerService) {
        this.gameManagerView = new GameManagerView();
        this.gameClientManagerService = new GameClientManagerService(clientHandlers);
        try {
                String codeRoom = ((SendEnterRoomPacket) packet).getCodeRoom();
                gameManagerView.showMessageEnterRoom(clientHandler.getClientId(), codeRoom);
                GameRoom gameRoom = gameManagerService.existRoom(codeRoom);
                gameClientManagerService.sendUpdates(clientHandler, new SendGameRoomPacket(gameRoom));
                if(gameRoom == null || gameRoom.getIsClosed()) {
                    return;
                }
                GameMatch gameMatch = gameManagerService.handleStartingGameMatch(gameRoom, clientHandler.getClientId());
                gameMatch.getListPlayers().forEach((player) -> {
                    gameClientManagerService.sendUpdateById(player.getPlayerId(), new SendStartingGameMatchPacket(gameMatch));
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }
}
