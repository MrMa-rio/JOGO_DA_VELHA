package src.main.jogo.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import src.main.jogo.models.GameMatch;
import src.main.jogo.models.GameRoom;
import src.main.jogo.models.Player;
import src.main.jogo.models.PlayerInMatch;
import src.main.jogo.net.packets.*;
import src.main.jogo.services.GameClientManagerService;
import src.main.jogo.services.GameManagerService;
import src.main.jogo.views.GameManagerView;

public class Server implements Runnable {
    public final static int DEFAULT_PORT_NUMBER = 1234;
    private ServerSocket serverSocket;
    private final ArrayList<ClientHandler> clientHandlers;
    private final GameManagerService gameManagerService;
    private final GameClientManagerService gameClientManagerService;
    private final GameManagerView gameManagerView;
    public Server(final int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        this.gameManagerService = new GameManagerService();
        this.gameClientManagerService = new GameClientManagerService(this, gameManagerService);
        this.gameManagerView = new GameManagerView();
        clientHandlers = new ArrayList<>();
    }

    public ArrayList<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    @Override
    public void run() {
        new Thread(this::startAcceptClientsLoop).start();
        new Thread(this::startGameloop).start();
    }
    private void startAcceptClientsLoop() {
        System.out.println("AGUARDANDO NOVOS JOGADORES");
        while (true) {
            try {
                final Socket socket = serverSocket.accept();
                System.out.println("UM JOGADOR SE CONECTOU");
                final ClientHandler clientHandler = new ClientHandler(this, socket);
                clientHandlers.add(clientHandler);
                Thread clientThread = new Thread(clientHandler); //
                clientThread.start();
                gameClientManagerService.sendUpdateForOthers(clientHandler, new SendMessagePacket("UM NOVO PLAYER SE CONECTOU"));
            } catch (final IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }
    private void startGameloop() {
      long lastTickTime = System.nanoTime();
        while (true) {
            int TICKS_PER_SECOND = 1; //
            int MILLISECONDS_PER_TICK = 1000000000 / TICKS_PER_SECOND;
            final long whenShouldNextTickRun = lastTickTime + MILLISECONDS_PER_TICK;
            if (System.nanoTime() < whenShouldNextTickRun) {
                continue;
            }
            gameClientManagerService.sendUpdatesToAll(new ClientPacket());
            lastTickTime = System.nanoTime();
        }
    }

    public void processPacket(final ClientHandler clientHandler, final ClientPacket packet) {
        System.out.println("Processando pacote...");
        if (packet instanceof final SendMessagePacket sendMessagePacket) {
            try {
                String message = sendMessagePacket.getMessage();
                System.out.println("O player "+ clientHandler.getClientId() + "\n" + "enviou a seguinte mensagem: " + message);
                gameClientManagerService.sendUpdateForOthers(clientHandler,sendMessagePacket);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if (packet instanceof final SendPlayerPacket sendPlayerPacket ) {
            try{
                Player player = sendPlayerPacket.getPlayer();
                gameManagerService.setGuestPlayersInList(player);
                gameManagerService.showListGuestPlayers();
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        else if(packet instanceof final SendCreateRoomPacket sendCreateRoomPacket){
            try {
                GameRoom gameRoom = sendCreateRoomPacket.getGameRoom();
                gameManagerView.showMessage(clientHandler.getClientId(), gameRoom.getCodeRoom());
                gameManagerService.handleCreateRoom(gameRoom);
                gameClientManagerService.sendUpdateForOthers(clientHandler, sendCreateRoomPacket);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if (packet.getClass() == SendGetGameRoomsPacket.class) {
            gameClientManagerService.sendUpdates(clientHandler, new SendGetGameRoomsPacket(gameManagerService.getListGameRooms()));
        }
        else if(packet instanceof final SendEnterRoomPacket sendEnterRoomPacket){
            try {
                String codeRoom = sendEnterRoomPacket.getCodeRoom();
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
        else if(packet.getClass() == SendStartedGameMatchPacket.class){
            GameMatch gameMatch = ((SendStartedGameMatchPacket) packet).getGameMatch();
            String codeRoom = gameMatch.getGameRoom().getCodeRoom();
            String hostId = gameMatch.getGameRoom().getHostId();
            Player player = gameManagerService.getGuestPlayerById(hostId).get(); //fazer a desserialização em uma classe apropriada
            if (!gameManagerService.getGameMatchInList(codeRoom).getIsStart()){ // fazer essa tratativa em uma classe apropriada
                System.out.println("RECEBENDO PARTIDA INICIADA");
                gameManagerService.getGameMatchInList(gameMatch.getGameRoom().getCodeRoom()).setStart(true);
                gameClientManagerService.sendUpdateById(hostId, new SendStateGameBoardPacket(new PlayerInMatch(player.getPlayerId(), player.getPlayerName())));
            }
        }
        else if (packet.getClass() == SendStateGameBoardPacket.class) {
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
        else if (packet.getClass() == SendCloseGameRoomPacket.class) {
            gameManagerService.handleClosingGameRoom(((SendCloseGameRoomPacket) packet).getCodeRoom());
            System.out.println("FECHANDO SALA DA PARTIDA...");
            gameClientManagerService.sendUpdates(clientHandler, new SendQuitGameMatchPacket());
        } else if (packet instanceof final SendDisconnectPacket sendDisconnectPacket) {
            clientHandler.disconnect();
            System.out.println("REMOVE DADOS DO JOGADOR");
            clientHandlers.remove(clientHandler);
            gameClientManagerService.sendUpdateForOthers(clientHandler, sendDisconnectPacket);
        }
    }



    public void closeServer() {
        try {
            serverSocket.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(final String[] args) {
        new Server(Server.DEFAULT_PORT_NUMBER).run();
    }
}