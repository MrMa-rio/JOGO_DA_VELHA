package src.main.shooter.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import src.main.shooter.game.ClientGame;
import src.main.shooter.game.action.SendMessage;
import src.main.shooter.net.packets.SendMessagePacket;
import src.main.shooter.net.packets.ClientPacket;
import src.main.shooter.net.packets.DisconnectPacket;

public class Client implements Runnable {
    private boolean isRunning;
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private ClientGame game;


    public ClientGame getGame() {
        return game;
    }

    public Client( final String ipAddress, final int port) throws IOException {
        socket = new Socket(ipAddress, port);
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());


        initialServerCommunication();
    }

    @SuppressWarnings("unchecked")
    private void initialServerCommunication() {
        try {

            final Object object = inputStream.readObject();
            var teste = object;
            game = new ClientGame(this, inputStream.toString());
            System.out.println(teste.toString());
            System.out.println("FINALIZA A COMUNICACAO INICIAL");
        } catch (final RuntimeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        isRunning = true;
        new Thread(() -> startReadAndWriteLoop()).start();
        new Thread(() -> startGameloop()).start();
    }

    private void startReadAndWriteLoop() {
        while (isRunning) {
            try {
                // read
               Object teste = inputStream.readObject();
               String teste2 = (String) teste;
               System.out.println(teste2);

                // write
                sendPacket(new SendMessagePacket(new SendMessage("ESCREVENDO ALGUMA COISA PARA O SERVIDOR")));
            }catch ( Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void startGameloop() {
        while (isRunning) {
            game.tick();

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

    public void disconnect() {
        System.out.println("DESCONEXAO PELO SERVIDOR ");

        isRunning = false;

        // tell server we disconnected
        sendPacket(new DisconnectPacket());

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
