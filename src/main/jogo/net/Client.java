package src.main.jogo.net;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

import src.main.jogo.net.packets.*;

public class Client implements Runnable {
    private boolean isConnected;
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private String clientId = UUID.randomUUID().toString();
    private Thread startRead;
    private Thread startWrite;

    public Client( final String ipAddress, final int port) {
        try {
            socket = new Socket(ipAddress, port);
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initialClientCommunication();
    }
    public String getClientId() {
        return clientId;
    }
    @Override
    public void run() {
        isConnected = true;
        startRead = new Thread(this::startReadLoop);
        startWrite = new Thread(this::startWriteLoop);
        startRead.start();
        startWrite.start();

    }
    private void initialClientCommunication() {
        try {
            outputStream.writeObject(new SendClientPacket(getClientId()));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
    private void startWriteLoop() {
        while (isConnected) {
            try {
                //sendPacket(new SendMessagePacket(scanner.nextLine()));
            }catch ( Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void startReadLoop() {
        while (isConnected) {
            try {
                ClientPacket clientPacket = (ClientPacket) inputStream.readObject();
                processPacket(clientPacket);
            }catch ( Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void sendPacket(final ClientPacket packet) {
        try {
            outputStream.writeObject(packet);
            outputStream.reset();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
    public void processPacket(ClientPacket packet){

        if(packet instanceof final SendMessagePacket sendMessagePacket){
            System.out.println(sendMessagePacket.getMessage());
        } else if (packet instanceof final SendClientPacket sendClientPacket) {
            this.clientId = sendClientPacket.getClientId();
        } else if (packet instanceof final SendPositionPacket sendPositionPacket) {
            System.out.println(sendPositionPacket.getMessage());
        }
        else if (packet instanceof final DisconnectPacket disconnectPacket) {
            System.out.println(disconnectPacket.getMessage());
        }
    }
    public void disconnect() {
        System.out.println("DESCONEXAO PELO SERVIDOR ");
        isConnected = false;

        // tell server we disconnected
        sendPacket(new DisconnectPacket("DESCONEXAO PELO SERVIDOR"));

        // close everything
        try {
            socket.close();
            inputStream.close();
            outputStream.close();
            startRead.interrupt();
            startWrite.interrupt();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
