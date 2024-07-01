package src.main.jogo.components.Executors;

import src.main.jogo.models.GameMatch;
import src.main.jogo.models.PlayerInMatch;
import src.main.jogo.net.ClientHandler;
import src.main.jogo.net.packets.ClientPacket;
import src.main.jogo.net.packets.SendStateGameBoardPacket;
import src.main.jogo.net.packets.SendWinLoseOrTiePacket;
import src.main.jogo.services.GameClientManagerService;
import src.main.jogo.services.GameManagerService;
import src.main.jogo.components.IExecuteSendCommand;

import java.util.ArrayList;

public class ExecuteSendStateGameBoard implements IExecuteSendCommand {
    GameClientManagerService gameClientManagerService;
    public ExecuteSendStateGameBoard(ArrayList<ClientHandler> clientHandlers, ClientPacket packet, GameManagerService gameManagerService) {
        this.gameClientManagerService = new GameClientManagerService(clientHandlers);
        System.out.println("Recebendo estado do tabuleiro...");
            GameMatch gameMatch = gameManagerService.getGameMatchInList(((SendStateGameBoardPacket) packet).getCodeRoom());
            String position = ((SendStateGameBoardPacket) packet).getPosition();
            PlayerInMatch playerInMatch = ((SendStateGameBoardPacket) packet).getPlayerInMatch();
            String codeRoom = gameMatch.getGameRoom().getCodeRoom();
            GameMatch gameMatchUpdated = gameManagerService.getGameMatchUpdated(gameMatch, playerInMatch, position);
            String verifyGameBoard = gameManagerService.handleVerifyGameBoard(gameMatchUpdated, playerInMatch);
            String nextPlayerId = gameMatch.getNextPlayer(playerInMatch.getPlayerId()); //Passando player anterior para que ele descubra o proximo player
            PlayerInMatch nextPlayer = gameMatch.getPlayerInListPlayersById(nextPlayerId);
            gameClientManagerService.sendUpdateById(playerInMatch.getPlayerId(), new SendWinLoseOrTiePacket(verifyGameBoard));
            gameClientManagerService.sendUpdateById(nextPlayerId, new SendWinLoseOrTiePacket(verifyGameBoard));
            gameManagerService.handleUpdateGameMatchForPlayers(gameMatchUpdated, clientHandlers);
            if(!gameMatchUpdated.getIsClosed()){
                gameClientManagerService.sendUpdateById(nextPlayerId, new SendStateGameBoardPacket(nextPlayer, codeRoom , position, playerInMatch.getXO() ));
            }

    }
}
