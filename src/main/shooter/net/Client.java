package src.main.shooter.net;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import src.main.shooter.game.ClientGame;
import src.main.shooter.game.action.SendMessage;
import src.main.shooter.net.packets.SendMessagePacket;
import src.main.shooter.net.packets.ClientPacket;
import src.main.shooter.net.packets.DisconnectPacket;
import src.main.shooter.net.packets.SendPositionPacket;

public class Client implements Runnable {
    private boolean isConnected;
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private ClientGame game;
    private Scanner scanner = new Scanner(System.in);

    public ClientGame getGame() {
        return game;
    }

    public Client( final String ipAddress, final int port) throws IOException {
        socket = new Socket(ipAddress, port);
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        initialServerCommunication();
    }

    // Server se comunicando com o Cliente
    private void initialServerCommunication() {
        try {
            //final Object object = inputStream.readObject();
            //game = new ClientGame(this, inputStream.toString());
           // System.out.println("FINALIZA A COMUNICACAO INICIAL");
        } catch (final RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        isConnected = true;
        new Thread(this::startReadLoop).start();
        new Thread(this::startWriteLoop).start();
        //new Thread(() -> startGameloop()).start();
    }

    private void startWriteLoop() {
        while (isConnected) {
            try {
                sendPacket(new SendMessagePacket(new SendMessage(scanner.next())));
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

//    private void startGameloop() {
//        while (isConnected) {
//            //game.tick();
//
//        }
//    }

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
            System.out.println(sendMessagePacket.SendMessage.getMessage());
        } else if (packet instanceof final SendPositionPacket sendPositionPacket) {
            System.out.println(sendPositionPacket.SendMessage.getMessage());
        }
        else if (packet instanceof final DisconnectPacket disconnectPacket) {
            System.out.println(disconnectPacket.SendMessage.getMessage());
        }
    }

    public void disconnect() {
        System.out.println("DESCONEXAO PELO SERVIDOR ");

        isConnected = false;

        // tell server we disconnected
        sendPacket(new DisconnectPacket(new SendMessage("DESCONEXAO PELO SERVIDOR")));

        // close everything
        try {
            socket.close();
            inputStream.close();
            outputStream.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(final String[] args) throws IOException {
        new Client("192.168.3.18", 1234).run();
    }
}
