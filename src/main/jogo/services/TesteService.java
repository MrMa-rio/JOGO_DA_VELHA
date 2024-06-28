package src.main.jogo.services;

import src.main.jogo.net.ClientHandler;
import src.main.jogo.net.packets.*;
import src.main.jogo.services.Teste.Teste2;
import src.main.jogo.services.Teste.Teste22.*;

public class TesteService {

    Teste2 teste2;

    public TesteService(){
        teste2 = new Teste2();
        teste2.execute(new Teste4());
    }

//    public void processPacket(final ClientHandler clientHandler, final ClientPacket packet) {
//        System.out.println("Processando pacote...");
//        if (packet instanceof final SendMessagePacket sendMessagePacket) {
//            try {
//                String message = sendMessagePacket.getMessage();
//                System.out.println("O player "+ clientHandler.getClientId() + "\n" + "enviou a seguinte mensagem: " + message);
//                gameClientManagerService.sendUpdateForOthers(clientHandler,sendMessagePacket);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//        else if (packet instanceof final SendPlayerPacket sendPlayerPacket ) {
//            try{
//                Player player = sendPlayerPacket.getPlayer();
//                gameManagerService.setGuestPlayersInList(player);
//                gameManagerService.showListGuestPlayers();
//            } catch (Exception e){
//                throw new RuntimeException(e);
//            }
//        }
//        else if(packet instanceof final SendCreateRoomPacket sendCreateRoomPacket){
//            try {
//                GameRoom gameRoom = sendCreateRoomPacket.getGameRoom();
//                gameManagerView.showMessage(clientHandler.getClientId(), gameRoom.getCodeRoom());
//                gameManagerService.handleCreateRoom(gameRoom);
//                gameClientManagerService.sendUpdateForOthers(clientHandler, sendCreateRoomPacket);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//        else if (packet.getClass() == SendGetGameRoomsPacket.class) {
//            gameClientManagerService.sendUpdates(clientHandler, new SendGetGameRoomsPacket(gameManagerService.getListGameRooms()));
//        }
//        else if(packet instanceof final SendEnterRoomPacket sendEnterRoomPacket){
//            try {
//                String codeRoom = sendEnterRoomPacket.getCodeRoom();
//                gameManagerView.showMessageEnterRoom(clientHandler.getClientId(), codeRoom);
//                GameRoom gameRoom = gameManagerService.existRoom(codeRoom);
//                gameClientManagerService.sendUpdates(clientHandler, new SendGameRoomPacket(gameRoom));
//                if(gameRoom == null || gameRoom.getIsClosed()) {
//                    return;
//                }
//                GameMatch gameMatch = gameManagerService.handleStartingGameMatch(gameRoom, clientHandler.getClientId());
//                gameMatch.getListPlayers().forEach((player) -> {
//                    gameClientManagerService.sendUpdateById(player.getPlayerId(), new SendStartingGameMatchPacket(gameMatch));
//                });
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//        else if(packet.getClass() == SendStartedGameMatchPacket.class){
//            GameMatch gameMatch = ((SendStartedGameMatchPacket) packet).getGameMatch();
//            String codeRoom = gameMatch.getGameRoom().getCodeRoom();
//            String hostId = gameMatch.getGameRoom().getHostId();
//            Player player = gameManagerService.getGuestPlayerById(hostId).get(); //fazer a desserialização em uma classe apropriada
//            if (!gameManagerService.getGameMatchInList(codeRoom).getIsStart()){ // fazer essa tratativa em uma classe apropriada
//                System.out.println("RECEBENDO PARTIDA INICIADA");
//                gameManagerService.getGameMatchInList(gameMatch.getGameRoom().getCodeRoom()).setStart(true);
//                gameClientManagerService.sendUpdateById(hostId, new SendStateGameBoardPacket(new PlayerInMatch(player.getPlayerId(), player.getPlayerName())));
//            }
//        }
//        else if (packet.getClass() == SendStateGameBoardPacket.class) {
//            System.out.println("Recebendo estado do tabuleiro...");
//            GameMatch gameMatch = gameManagerService.getGameMatchInList(((SendStateGameBoardPacket) packet).getCodeRoom());
//            String position = ((SendStateGameBoardPacket) packet).getPosition();
//            PlayerInMatch playerInMatch = ((SendStateGameBoardPacket) packet).getPlayerInMatch();
//            String codeRoom = gameMatch.getGameRoom().getCodeRoom();
//            GameMatch gameMatchUpdated = gameManagerService.getGameMatchUpdated(gameMatch, playerInMatch, position);
//            String verifyGameBoard = gameManagerService.handleVerifyGameBoard(gameMatchUpdated, playerInMatch);
//            String nextPlayerId = gameMatch.getNextPlayer(playerInMatch.getPlayerId()); //Passando player anterior para que ele descubra o proximo player
//            PlayerInMatch nextPlayer = gameMatch.getPlayerInListPlayersById(nextPlayerId);
//            gameClientManagerService.sendUpdateById(playerInMatch.getPlayerId(), new SendWinLoseOrTiePacket(verifyGameBoard));
//            gameClientManagerService.sendUpdateById(nextPlayerId, new SendWinLoseOrTiePacket(verifyGameBoard));
//            gameManagerService.handleUpdateGameMatchForPlayers(gameMatchUpdated, clientHandlers);
//            if(!gameMatchUpdated.getIsClosed()){
//                gameClientManagerService.sendUpdateById(nextPlayerId, new SendStateGameBoardPacket(nextPlayer, codeRoom , position, playerInMatch.getXO() ));
//            }
//        }
//        else if (packet.getClass() == SendCloseGameRoomPacket.class) {
//            gameManagerService.handleClosingGameRoom(((SendCloseGameRoomPacket) packet).getCodeRoom());
//            System.out.println("FECHANDO SALA DA PARTIDA...");
//            gameClientManagerService.sendUpdates(clientHandler, new SendQuitGameMatchPacket());
//        } else if (packet instanceof final SendDisconnectPacket sendDisconnectPacket) {
//            clientHandler.disconnect();
//            System.out.println("REMOVE DADOS DO JOGADOR");
//            clientHandlers.remove(clientHandler);
//            gameClientManagerService.sendUpdateForOthers(clientHandler, sendDisconnectPacket);
//        }
//    }
    public void processPacket2(final ClientHandler clientHandler, final ClientPacket packet) {
        System.out.println("Processando pacote...");

        if (packet instanceof final SendMessagePacket sendMessagePacket) {
            teste2.execute(new Teste4(clientHandler, packet));
        }
        else if (packet instanceof final SendPlayerPacket sendPlayerPacket ) {
            teste2.execute(new Teste5(clientHandler, packet));
        }
        else if(packet instanceof final SendCreateRoomPacket sendCreateRoomPacket){
            teste2.execute(new Teste6(clientHandler, packet));
        }
        else if (packet.getClass() == SendGetGameRoomsPacket.class) {
            teste2.execute(new Teste7(clientHandler, packet));
        }
        else if(packet instanceof final SendEnterRoomPacket sendEnterRoomPacket){
            teste2.execute(new Teste8(clientHandler, packet));
        }
        else if(packet.getClass() == SendStartedGameMatchPacket.class){
            teste2.execute(new Teste9(clientHandler, packet));
        }
        else if (packet.getClass() == SendStateGameBoardPacket.class) {
            teste2.execute(new Teste10(clientHandler, packet));
        }
        else if (packet.getClass() == SendCloseGameRoomPacket.class) {
            teste2.execute(new Teste4(clientHandler, packet));
        } else if (packet instanceof final SendDisconnectPacket sendDisconnectPacket) {
            teste2.execute(new Teste4(clientHandler, packet));
        }
    }
}
