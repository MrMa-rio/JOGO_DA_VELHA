package src.main.jogo.components.Executors;

import src.main.jogo.models.GameMatch;
import src.main.jogo.models.Player;
import src.main.jogo.models.PlayerInMatch;
import src.main.jogo.net.ClientHandler;
import src.main.jogo.net.packets.ClientPacket;
import src.main.jogo.net.packets.SendStartedGameMatchPacket;
import src.main.jogo.net.packets.SendStateGameBoardPacket;
import src.main.jogo.services.GameClientManagerService;
import src.main.jogo.services.GameManagerService;
import src.main.jogo.components.IExecuteSendCommand;

import java.util.ArrayList;

public class ExecuteSendStartedGameMatch implements IExecuteSendCommand {
    GameClientManagerService gameClientManagerService;

    public ExecuteSendStartedGameMatch(ArrayList<ClientHandler> clientHandlers, ClientPacket packet, GameManagerService gameManagerService) {
        this.gameClientManagerService = new GameClientManagerService(clientHandlers);
        GameMatch gameMatch = ((SendStartedGameMatchPacket) packet).getGameMatch();
        String codeRoom = gameMatch.getGameRoom().getCodeRoom();
        String hostId = gameMatch.getGameRoom().getHostId();
        Player player = gameManagerService.getGuestPlayerById(hostId).get(); //fazer a desserialização em uma classe apropriada
        if (!gameManagerService.getGameMatchInList(codeRoom).getIsStart()) { // fazer essa tratativa em uma classe apropriada
            System.out.println("RECEBENDO PARTIDA INICIADA");
            gameManagerService.getGameMatchInList(gameMatch.getGameRoom().getCodeRoom()).setStart(true);
            gameClientManagerService.sendUpdateById(hostId, new SendStateGameBoardPacket(new PlayerInMatch(player.getPlayerId(), player.getPlayerName())));
        }
    }
}
