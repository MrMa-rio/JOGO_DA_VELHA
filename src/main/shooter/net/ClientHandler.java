package src.main.shooter.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import src.main.shooter.net.packets.ClientPacket;

public class ClientHandler implements Runnable {
    private boolean isRunning;
    private final String clientId;
    private final Server server;
    private final Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public String getClientId() {
        return clientId;
    }

    public ClientHandler(final Server server, final Socket socket, final String id) {
        this.server = server;
        this.socket = socket;
        this.clientId = id;

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
            outputStream.writeObject(clientId);
            server.sendUpdates(this);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        isRunning = true;
        startReceiveMessageLoop();
    }

    // client to server
    private void startReceiveMessageLoop() {
        while (isRunning) {
            try {
                final ClientPacket packet = (ClientPacket) inputStream.readObject();
                server.processPacket(this, packet);
            } catch (final ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // server to client
    public void sendUpdate(String update) { //aqui poderia ser enviado o stateGame
        if (!isRunning) {
            return;
        }

        try {
            outputStream.writeObject(update);
            outputStream.reset();
        } catch (final IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        System.out.println("UM JOGADOR SE DESCONECTOU");

        isRunning = false;

        // close everything
        try {
            socket.close();
            inputStream.close();
            outputStream.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
