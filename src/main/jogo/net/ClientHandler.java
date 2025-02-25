package src.main.jogo.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import src.main.jogo.net.packets.ClientPacket;
import src.main.jogo.net.packets.SendDisconnectPacket;
import src.main.jogo.net.packets.SendClientPacket;
import src.main.jogo.services.ExecutorSendPacketService;

public class ClientHandler implements Runnable {
    private boolean isConnected;
    private String clientId;
    private final Server server;
    private final Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private final ExecutorSendPacketService executorSendPacketService;
    public String getClientId() {
        return clientId;
    }

    public ClientHandler(final Server server, final Socket socket, ExecutorSendPacketService executorSendPacketService) {
        this.executorSendPacketService = executorSendPacketService;
        this.server = server;
        this.socket = socket;
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
            SendClientPacket sendClientPacket = (SendClientPacket) inputStream.readObject();
            this.clientId = sendClientPacket.getClientId();
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        isConnected = true;
        startReceiveMessageLoop();
    }
    private void startReceiveMessageLoop() {
        while (isConnected) {
            try {
                final ClientPacket packet = (ClientPacket) inputStream.readObject();
                executorSendPacketService.processPacket(this, server.getClientHandlers(), packet);
            } catch (final ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {

            }
        }
    }

    public void sendPacket(ClientPacket packet) {
        if (!isConnected) {
            return;
        }
        try {
            outputStream.writeObject(packet);
            outputStream.reset();

        } catch (final IOException e) {
            System.out.println(e.getMessage());
            disconnect();
        }
    }
    public void disconnect() {
        System.out.println("UM JOGADOR SE DESCONECTOU");
        isConnected = false;
        sendPacket(new SendDisconnectPacket("UM PLAYER DESCONECTOU"));
        try {
            socket.close();
            inputStream.close();
            outputStream.close();
        } catch (final IOException e) {
            System.out.println(e.getMessage());
        }
    }
}