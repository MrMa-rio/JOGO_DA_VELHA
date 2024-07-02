package src.main.jogo.services;

import src.main.jogo.components.IExecuteSendCommand;
import src.main.jogo.net.ClientHandler;
import src.main.jogo.net.packets.*;
import src.main.jogo.components.ExecutorSendPacket;
import src.main.jogo.components.Executors.*;

import java.util.ArrayList;

public class ExecutorSendPacketService {

    private final ExecutorSendPacket executorSendPacket;
    private final GameManagerService gameManagerService;

    public ExecutorSendPacketService() {
        this.gameManagerService = new GameManagerService();
        executorSendPacket = new ExecutorSendPacket();
    }

    public void processPacket(final ClientHandler clientHandler, final ArrayList<ClientHandler> clientHandlers, final ClientPacket packet) {
        System.out.println("Processando pacote...");
        IExecuteSendCommand executeSendCommand = null;
        if (packet.getClass() == SendMessagePacket.class) {
            executeSendCommand = new ExecuteSendMessage(clientHandler, clientHandlers, packet);
        } else if (packet.getClass() == SendPlayerPacket.class) {
            executeSendCommand = new ExecuteSendPlayer(clientHandler, gameManagerService, packet);
        } else if (packet.getClass() == SendCreateRoomPacket.class) {
            executeSendCommand = new ExecuteSendCreateRoom(clientHandler, clientHandlers, packet, gameManagerService);
        } else if (packet.getClass() == SendGetGameRoomsPacket.class) {
            executeSendCommand = new ExecuteGetGameRooms(clientHandler, clientHandlers, gameManagerService);
        } else if (packet.getClass() == SendEnterRoomPacket.class) {
            executeSendCommand = new ExecuteSendEnterRoom(clientHandler, clientHandlers, packet, gameManagerService);
        } else if (packet.getClass() == SendStartedGameMatchPacket.class) {
            executeSendCommand = new ExecuteSendStartedGameMatch(clientHandlers, packet, gameManagerService);
        } else if (packet.getClass() == SendStateGameBoardPacket.class) {
            executeSendCommand = new ExecuteSendStateGameBoard(clientHandlers, packet, gameManagerService);
        } else if (packet.getClass() == SendCloseGameRoomPacket.class) {
            executeSendCommand = new ExecuteSendCloseGameRoom(clientHandler, clientHandlers, packet, gameManagerService);
        } else if (packet.getClass() == SendDisconnectPacket.class) {
            executeSendCommand = new ExecuteSendDisconnect(clientHandler, clientHandlers, packet);
        }
        executorSendPacket.execute(executeSendCommand);
    }
}
