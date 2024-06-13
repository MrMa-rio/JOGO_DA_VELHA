package src.main.jogo.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import src.main.jogo.models.GameRoom;
import src.main.jogo.models.Player;
import src.main.jogo.net.packets.*;
import src.main.jogo.services.GameManagerService;

public class Server implements Runnable {
    public final static int DEFAULT_PORT_NUMBER = 1234;
    private ServerSocket serverSocket;
    private final ArrayList<ClientHandler> clientHandlers;
    private GameManagerService gameManagerService;
    public Server(final int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        this.gameManagerService = new GameManagerService();
        clientHandlers = new ArrayList<>();
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
                new Thread(clientHandler).start();
                sendUpdateForOthers(clientHandler, new SendMessagePacket("UM NOVO PLAYER SE CONECTOU"));
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
            int TICKS_PER_SECOND = 20;
            int MILLISECONDS_PER_TICK = 1000000000 / TICKS_PER_SECOND;
            final long whenShouldNextTickRun = lastTickTime + MILLISECONDS_PER_TICK;
            if (System.nanoTime() < whenShouldNextTickRun) {
                continue;
            }
            sendUpdatesToAll();
           lastTickTime = System.nanoTime();
        }
    }

    public void processPacket(final ClientHandler clientHandler, final ClientPacket packet) {
        System.out.println("Processando pacote...");
        if (packet instanceof final SendMessagePacket sendMessagePacket) {
            try {
                String message = sendMessagePacket.getMessage();
                System.out.println("O player "+ clientHandler.getClientId() + "\n" + "enviou a seguinte mensagem: " + message);
                sendUpdateForOthers(clientHandler,sendMessagePacket);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if (packet instanceof final SendPlayerPacket sendPlayerPacket ) {
            Player player = sendPlayerPacket.getPlayer();
            gameManagerService.setGuestPlayersInList(player);
            gameManagerService.showListGuestPlayers();
        }
        else if(packet instanceof final SendCreateRoomPacket sendCreateRoomPacket){
            try {
                GameRoom gameRoom = sendCreateRoomPacket.getGameRoom();
                System.out.println("O player "
                        + clientHandler.getClientId()
                        + "\n" + "criou uma sala com o nome de: "
                        + gameRoom.getCodeRoom());
                gameManagerService.handleCreateRoom(gameRoom);
                sendUpdateForOthers(clientHandler, sendCreateRoomPacket);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if(packet instanceof final SendEnterRoomPacket sendEnterRoomPacket){
            try {
                String codeRoom = sendEnterRoomPacket.getCodeRoom();
                System.out.println("O player "
                        + clientHandler.getClientId()
                        + "\n" + "quer entrar em uma sala com o nome de: "
                        + codeRoom);
                GameRoom gameRoom = gameManagerService.existRoom(codeRoom);
                sendUpdates(clientHandler, new SendGameRoomPacket(gameRoom));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if (packet instanceof final DisconnectPacket disconnectPacket) {
            clientHandler.disconnect();
            System.out.println("REMOVE DADOS DO JOGADOR");
            clientHandlers.remove(clientHandler);
            sendUpdateForOthers(clientHandler, disconnectPacket);
        }
    }
    private void sendUpdatesToAll() {
        for (final ClientHandler clientHandler : clientHandlers) {
            sendUpdates(clientHandler);
        }
    }
    public void sendUpdates(final ClientHandler clientHandler, ClientPacket packet) {
        clientHandler.sendUpdate(packet);
    }
    public void sendUpdates(final ClientHandler clientHandler) {
        clientHandler.sendUpdate(new ClientPacket());
    }
    public void sendUpdateForOthers(ClientHandler clientHandler, ClientPacket packet){
        for (final ClientHandler otherClientHandler : clientHandlers) {
            if(clientHandler != otherClientHandler){
                sendUpdates(otherClientHandler, packet);
            }
        }
    }
    public void closeServer() {
        try {
            serverSocket.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
    public ServerSocket getServerSocket() {
        return serverSocket;
    }
    public static void main(final String[] args) {
        new Server(Server.DEFAULT_PORT_NUMBER).run();
    }
}