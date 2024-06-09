package src.main.shooter.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import src.main.shooter.game.action.SendMessage;
import src.main.shooter.net.packets.DisconnectPacket;
import src.main.shooter.net.packets.ClientPacket;
import src.main.shooter.net.packets.SendMessagePacket;

public class Server implements Runnable {
    private final int TICKS_PER_SECOND = 20;
    private final int MILLISECONDS_PER_TICK = 1000000000 / TICKS_PER_SECOND;

    public final static int DEFAULT_PORT_NUMBER = 1234;
    private ServerSocket serverSocket;
    private final ArrayList<PlayerHandler> playerHandlers;

    public Server(final int port) {

        try {
            this.serverSocket = new ServerSocket(port);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        playerHandlers = new ArrayList<PlayerHandler>();
    }

    @Override
    public void run() {
        new Thread(() -> startAcceptClientsLoop()).start();
        new Thread(() -> startGameloop()).start();
    }

    private void startAcceptClientsLoop() {
        System.out.println("AGUARDANDO NOVOS JOGADORES");
        while (true) {
            try {
                final Socket socket = serverSocket.accept();
                System.out.println("UM JOGADOR SE CONECTOU");
                final PlayerHandler playerHandler = new PlayerHandler(this, socket, UUID.randomUUID().toString());
                playerHandlers.add(playerHandler);
                new Thread(playerHandler).start();
                sendUpdateForOthers(playerHandler, new SendMessagePacket(new SendMessage("UM NOVO PLAYER SE CONECTOU")));
            } catch (final IOException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    private void startGameloop() {
        long lastTickTime = System.nanoTime();

        while (true) {
            final long whenShouldNextTickRun = lastTickTime + MILLISECONDS_PER_TICK;
            if (System.nanoTime() < whenShouldNextTickRun) {
                continue;
            }
            sendUpdatesToAll();
            lastTickTime = System.nanoTime();
        }
    }

    public void processPacket(final PlayerHandler playerHandler, final ClientPacket packet) {
        System.out.println("Processando pacote...");

        if (packet instanceof final SendMessagePacket sendMessagePacket) {
            try {
                String message = sendMessagePacket.SendMessage.getMessage();
                System.out.println("O player "+ playerHandler.getPlayerId() + "\n" + "enviou a seguinte mensagem: " + message);
                sendUpdateForOthers(playerHandler,sendMessagePacket);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
//        else if(packet instanceof final SendPositionPacket sendPositionPacket){
//
//            try {
//                String message = sendPositionPacket.SendMessage.getMessage();
//                System.out.println("O player "+ playerHandler.getClientId() + "\n" + "enviou a seguinte mensagem: " + message);
//                sendUpdateForOthers(playerHandler, sendPositionPacket);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
        else if (packet instanceof final DisconnectPacket disconnectPacket) {
            playerHandler.disconnect();
            System.out.println("REMOVE DADOS DO JOGADOR");
            playerHandlers.remove(playerHandler);
            sendUpdateForOthers(playerHandler, disconnectPacket);
        }
        //sendUpdatesToAll(packet);
    }

    // server to all client
    private void sendUpdatesToAll() {
        for (final PlayerHandler playerHandler : playerHandlers) {
            sendUpdates(playerHandler);
        }
    }

    // server to one client
    public void sendUpdates(final PlayerHandler playerHandler, ClientPacket packet) {
        playerHandler.sendUpdate(packet);
    }
    public void sendUpdates(final PlayerHandler playerHandler) {
        playerHandler.sendUpdate(new ClientPacket());
    }

    public void sendUpdateForOthers(PlayerHandler playerHandler, ClientPacket packet){
        for (final PlayerHandler otherPlayerHandler : playerHandlers) {
            if(playerHandler != otherPlayerHandler){
                sendUpdates(otherPlayerHandler, packet);
            }
        }
    }

    public void closeServer() {
        // TODO: save state or something

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
