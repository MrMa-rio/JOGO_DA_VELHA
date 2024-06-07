package src.main.shooter.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import src.main.shooter.net.packets.DisconnectPacket;
import src.main.shooter.net.packets.ClientPacket;

public class Server implements Runnable {
    private final int TICKS_PER_SECOND = 20;
    private final int MILLISECONDS_PER_TICK = 1000000000 / TICKS_PER_SECOND;

    public final static int DEFAULT_PORT_NUMBER = 1234;
    private ServerSocket serverSocket;
    private final ArrayList<ClientHandler> clientHandlers;

    public Server(final int port) {

        try {
            this.serverSocket = new ServerSocket(port);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        clientHandlers = new ArrayList<ClientHandler>();
    }

    @Override
    public void run() {
        new Thread(() -> startAcceptClientsLoop()).start();
        new Thread(() -> startGameloop()).start();
    }

    private void startAcceptClientsLoop() {
        System.out.println("Accepting Clients.");
        while (true) {
            System.out.println("AGUARDANDO NOVOS JOGADORES");
            try {
                final Socket socket = serverSocket.accept();
                System.out.println("UM JOGADOR SE CONECTOU");
                final ClientHandler clientHandler = new ClientHandler(this, socket, UUID.randomUUID().toString());
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
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

    public void processPacket(final ClientHandler clientHandler, final ClientPacket packet) {
        if (packet instanceof final Object teste) {
            System.out.println("Processando Pacotes processPacket");
            try {
                System.out.println(packet.toString());
                System.out.println(clientHandler.getClientId());
                clientHandler.sendUpdate("TESTE");
                Thread.sleep(10000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (packet instanceof final DisconnectPacket disconnectPacket) {
            clientHandler.disconnect();
            System.out.println("REMOVE DADOS DO JOGADOR");
            clientHandlers.remove(clientHandler);
        }
    }

    // server to all client
    private void sendUpdatesToAll() {
        for (final ClientHandler clientHandler : clientHandlers) {
            sendUpdates(clientHandler);
        }
    }

    // server to one client
    public void sendUpdates(final ClientHandler clientHandler) {
        clientHandler.sendUpdate("Teste no metodo 'sendUpdates' ");
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
