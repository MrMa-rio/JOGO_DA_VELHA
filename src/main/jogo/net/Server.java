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
            gameClientManagerService.sendUpdatesToAll(new ServerPacket());
            lastTickTime = System.nanoTime();
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