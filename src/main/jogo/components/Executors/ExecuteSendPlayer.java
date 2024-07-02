package src.main.jogo.components.Executors;

import src.main.jogo.models.Player;
import src.main.jogo.net.ClientHandler;
import src.main.jogo.net.packets.ClientPacket;
import src.main.jogo.net.packets.SendPlayerPacket;
import src.main.jogo.services.GameManagerService;
import src.main.jogo.components.IExecuteSendCommand;

public class ExecuteSendPlayer implements IExecuteSendCommand {
    private final GameManagerService gameManagerService;

    public ExecuteSendPlayer(ClientHandler clientHandler, GameManagerService gameManagerService, ClientPacket packet) {
        this.gameManagerService = gameManagerService;
        try {
            Player player = ((SendPlayerPacket) packet).getPlayer();
            gameManagerService.setGuestPlayersInList(player);
            gameManagerService.showListGuestPlayers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
