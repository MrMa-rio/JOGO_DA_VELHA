package src.main.jogo.net;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

import src.main.jogo.models.GameMatch;
import src.main.jogo.models.GameRoom;
import src.main.jogo.net.packets.*;
import src.main.jogo.services.GamePlayerService;

public class Client implements Runnable {
    private boolean isConnected;
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private final GamePlayerService gamePlayerService;
    private String clientId = UUID.randomUUID().toString();
    private Thread startRead;


    public Client( final String ipAddress, final int port) {
        try {
            socket = new Socket(ipAddress, port);
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.gamePlayerService = new GamePlayerService();
        initialClientCommunication();
    }
    public String getClientId() {
        return clientId;
    }
    @Override
    public void run() {
        isConnected = true;
        startRead = new Thread(this::startReadLoop);
        //startWrite = new Thread(this::startWriteLoop);
        startRead.start();
        //startWrite.start();
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
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                ClientPacket clientPacket = null;
                try {
                    clientPacket = (ClientPacket) inputStream.readObject();
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println(ex.getMessage());
                    throw new RuntimeException(ex);
                }
                processPacket(clientPacket); //Pode dar muito errado, foi analisado que as vezes o tick de leitura se esbarra com o tick de outro cliente gerando o erro stream active
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
        }
        else if (packet instanceof final SendClientPacket sendClientPacket) {
            this.clientId = sendClientPacket.getClientId();
        }
        else if (packet instanceof final SendGameRoomPacket sendGameRoomPacket){
            GameRoom gameRoom = sendGameRoomPacket.getGameRoom();
            gamePlayerService.handleGameRoomExist(gameRoom);
        }
        else if(packet instanceof final  SendStartingGameMatchPacket sendStartingGameMatchPacket){
            GameMatch gameMatch = sendStartingGameMatchPacket.getGameMatch();
            gamePlayerService.handleStartingMatching(gameMatch);
        }
        else if (packet.getClass() == SendGameBoardPacket.class) {
            gamePlayerService.handleShowBoardState(((SendGameBoardPacket) packet).getGameBoard());
        }
        else if (packet.getClass() == SendStateGameBoardPacket.class) {
            if(((SendStateGameBoardPacket) packet).getIsFirstMove()){
                System.out.println("Faca a primeira jogada");
                gamePlayerService.handleMovePlayer(((SendStateGameBoardPacket) packet).getPlayerInMatch().getXO(), "");
                return;
            }
            String XO =((SendStateGameBoardPacket) packet).getPlayerInMatch().getXO();
            String hostXO = ((SendStateGameBoardPacket) packet).getHostXO();
            System.out.println("==========================================================");
            System.out.println("Faca sua jogada");
            gamePlayerService.handleMovePlayer(XO, hostXO );
        }
        else if (packet instanceof final DisconnectPacket disconnectPacket) {
            System.out.println(disconnectPacket.getMessage());
        }
    }
    public void disconnect() {
        System.out.println("DESCONEXAO PELO SERVIDOR ");
        isConnected = false;
        sendPacket(new DisconnectPacket("DESCONEXAO PELO SERVIDOR"));
        try {
            socket.close();
            inputStream.close();
            outputStream.close();
            startRead.interrupt();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
