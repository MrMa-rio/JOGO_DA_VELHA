package src.main.jogo.services;

import src.main.jogo.net.ClientHandler;
import src.main.jogo.net.packets.*;
import src.main.jogo.components.ExecutorSendPacket;
import src.main.jogo.components.Executors.*;

import java.util.ArrayList;

public class ExecutorSendPacketService {

    ExecutorSendPacket executorSendPacket;
    GameManagerService gameManagerService;
    public ExecutorSendPacketService(){
        this.gameManagerService = new GameManagerService();
        executorSendPacket = new ExecutorSendPacket();
    }

    public void processPacket(final ClientHandler clientHandler, final ArrayList<ClientHandler> clientHandlers, final ClientPacket packet) {
        System.out.println("Processando pacote...");

        if (packet.getClass() == SendMessagePacket.class) {
            executorSendPacket.execute(new ExecuteSendMessage(clientHandler,clientHandlers, packet));
        }
        else if (packet.getClass() == SendPlayerPacket.class ) {
            executorSendPacket.execute(new ExecuteSendPlayer(clientHandler,gameManagerService, packet));
        }
        else if(packet.getClass() == SendCreateRoomPacket.class){
            executorSendPacket.execute(new ExecuteSendCreateRoom(clientHandler,clientHandlers, packet, gameManagerService));
        }
        else if (packet.getClass() == SendGetGameRoomsPacket.class) {
            executorSendPacket.execute(new ExecuteGetGameRooms(clientHandler,clientHandlers, gameManagerService));
        }
        else if(packet.getClass() == SendEnterRoomPacket.class){
            executorSendPacket.execute(new ExecuteSendEnterRoom(clientHandler,clientHandlers, packet, gameManagerService));
        }
        else if(packet.getClass() == SendStartedGameMatchPacket.class){
            executorSendPacket.execute(new ExecuteSendStartedGameMatch(clientHandlers, packet, gameManagerService));
        }
        else if (packet.getClass() == SendStateGameBoardPacket.class) {
            executorSendPacket.execute(new ExecuteSendStateGameBoard(clientHandlers, packet, gameManagerService));
        }
        else if (packet.getClass() == SendCloseGameRoomPacket.class) {
            executorSendPacket.execute(new ExecuteSendCloseGameRoom(clientHandler,clientHandlers, packet, gameManagerService));
        } else if (packet.getClass() == SendDisconnectPacket.class) {
            executorSendPacket.execute(new ExecuteSendDisconnect(clientHandler,clientHandlers, packet));
        }
    }
}
