package src.main.shooter.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import src.main.shooter.game.action.SendMessage;
import src.main.shooter.net.packets.ClientPacket;
import src.main.shooter.net.packets.DisconnectPacket;
import src.main.shooter.net.packets.SendMessagePacket;

public class PlayerHandler implements Runnable {
    private boolean isConnected;
    private final String playerId;
    private final Server server;
    private final Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    public String getPlayerId() {
        return playerId;
    }

    public PlayerHandler(final Server server, final Socket socket, final String id) {
        this.server = server;
        this.socket = socket;
        this.playerId = id;

        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (final IOException e) {
            e.printStackTrace();
        }

        initialClientCommunication();
    }

    //O servidor irá mandar mensagem para o cliente
    private void initialClientCommunication() {
        try {
            outputStream.writeObject(new SendMessagePacket(new SendMessage(playerId)));
            server.sendUpdates(this, new SendMessagePacket(new SendMessage("Iniciando Cliente atraves do servidor")));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        isConnected = true;
        startReceiveMessageLoop();
    }

    // client to server
    private void startReceiveMessageLoop() {
        while (isConnected) {
            try {
                final ClientPacket packet = (ClientPacket) inputStream.readObject();
                server.processPacket(this, packet);
            } catch (final ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                //throw new RuntimeException(e);
            }
        }
    }

    // server to client
    public void sendUpdate(ClientPacket update) { //aqui poderia ser enviado o stateGame
        if (!isConnected) {
            return;
        }

        try {
            outputStream.writeObject(update);
            outputStream.reset();

        } catch (final IOException e) {
            System.out.println(e.getMessage());
            disconnect();
        }
    }

    public void disconnect() {
        System.out.println("UM JOGADOR SE DESCONECTOU");
        isConnected = false;
        server.sendUpdateForOthers(this, new DisconnectPacket(new SendMessage("UM PLAYER DESCONECTOU   ")));
        // close everything
        try {
            socket.close();
            inputStream.close();
            outputStream.close();
        } catch (final IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
