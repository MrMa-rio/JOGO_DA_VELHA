package src.main.jogo.services;

import src.main.jogo.models.*;
import src.main.jogo.net.ClientHandler;
import src.main.jogo.net.packets.*;
import src.main.jogo.views.GameManagerView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class GameManagerService {
    private final ArrayList<Player> guestPlayers;
    private final ArrayList<GameRoom> listGameRooms;
    private final ArrayList<GameMatch> listGameMatches;
    private final GameManagerView gameManagerView;
    private final GameBoardService gameBoardService;
    private final GameVerifyBoardService gameVerifyBoardService;

    public GameManagerService() {
        this.guestPlayers = new ArrayList<>();
        this.listGameRooms = new ArrayList<>();
        this.listGameMatches = new ArrayList<>();
        this.gameManagerView = new GameManagerView();
        this.gameBoardService = new GameBoardService();
        this.gameVerifyBoardService = new GameVerifyBoardService();

    }

    public void setGameRoomsInList(GameRoom gameRoom) {
        this.listGameRooms.add(gameRoom);
    }

    public void setGuestPlayersInList(Player player) {
        this.guestPlayers.add(player);
    }

    public AtomicReference<Player> getGuestPlayerById(String guestPlayerId) {
        AtomicReference<Player> guestPlayer = new AtomicReference<>();
        guestPlayers.forEach((player) -> {
            if (player.getPlayerId().equals(guestPlayerId)) {
                guestPlayer.set(player);
            }
        });
        return guestPlayer;
    }

    public ArrayList<GameRoom> getListGameRooms() {
        return listGameRooms;
    }

    public GameMatch getGameMatchInList(String codeRoom) {
        AtomicReference<GameMatch> gameMatchAtomicReference = new AtomicReference<>();
        listGameMatches.forEach((gameMatchInList) -> {
            if (!gameMatchInList.getIsClosed() && Objects.equals(gameMatchInList.getGameRoom().getCodeRoom(), codeRoom)) {
                gameMatchAtomicReference.set(gameMatchInList);
            }
        });
        return gameMatchAtomicReference.get();
    }

    public GameRoom getGameRoomInList(String codeRoom) {
        AtomicReference<GameRoom> gameRoomAtomicReference = new AtomicReference<>();
        listGameRooms.forEach((gameRoomInList) -> {
            if (!gameRoomInList.getIsClosed() && Objects.equals(gameRoomInList.getCodeRoom(), codeRoom)) {
                gameRoomAtomicReference.set(gameRoomInList);
            }
        });
        return gameRoomAtomicReference.get();
    }

    public void showListGameRooms() {
        gameManagerView.showListGameRooms(listGameRooms);
    }

    public void showListGuestPlayers() {
        gameManagerView.showListGuestPlayersConnected(guestPlayers);
    }

    public void handleCreateRoom(GameRoom gameRoom) {
        setGameRoomsInList(gameRoom);
        showListGameRooms();
    }

    public GameRoom existRoom(String codeRoom) {
        AtomicReference<GameRoom> gameRoom = new AtomicReference<>();
        if (listGameRooms.isEmpty()) return gameRoom.get();
        listGameRooms.forEach((room) -> {
            if (room.getCodeRoom().equals(codeRoom)) {
                gameRoom.set(room);
            }
        });
        return gameRoom.get();
    }

    public GameMatch handleStartingGameMatch(GameRoom gameRoom, String guestPlayerId) {
        GameMatch gameMatch = new GameMatch();
        gameMatch.setGameRoom(gameRoom);
        gameBoardService.create(gameMatch.getGameBoard());
        AtomicReference<Player> hostPlayer = getGuestPlayerById(gameRoom.getHostId());
        AtomicReference<Player> guestPlayer = getGuestPlayerById(guestPlayerId);
        gameMatch.setPlayerInListPlayers(new PlayerInMatch(hostPlayer.get().getPlayerId(), hostPlayer.get().getPlayerName()));
        gameMatch.setPlayerInListPlayers(new PlayerInMatch(guestPlayer.get().getPlayerId(), guestPlayer.get().getPlayerName()));
        listGameMatches.add(gameMatch);
        return gameMatch;
    }

    public GameMatch handleUpdateGameMatch(String codeRoom, String position, String XO) {
        GameMatch gameMatch = getGameMatchInList(codeRoom);
        gameMatch.getGameBoard().setPosition(position, XO);
        return gameMatch;
    }

    public void handleUpdateGameMatchForPlayers(GameMatch gameMatch, ArrayList<ClientHandler> clientHandlers) {
        ArrayList<PlayerInMatch> listPlayers = gameMatch.getListPlayers();
        listPlayers.forEach((player) -> {
            clientHandlers.forEach((clientHandler) -> {
                if (Objects.equals(clientHandler.getClientId(), player.getPlayerId())) {
                    clientHandler.sendPacket(new SendGameBoardPacket(gameMatch.getGameBoard()));
                }

            });
        });

    }

    public String verifyGameBoard(GameMatch gameMatchUpdated, String XO, String playerName) {
        if (!gameVerifyBoardService.naHorizontal(XO, gameMatchUpdated.getGameBoard().getGameBoard(), playerName).isEmpty()) {
            return gameVerifyBoardService.naHorizontal(XO, gameMatchUpdated.getGameBoard().getGameBoard(), playerName);
        }
        if (!gameVerifyBoardService.naVertical(XO, gameMatchUpdated.getGameBoard().getGameBoard(), playerName).isEmpty()) {
            return gameVerifyBoardService.naVertical(XO, gameMatchUpdated.getGameBoard().getGameBoard(), playerName);
        }
        if (!gameVerifyBoardService.naSemiCruzDirEsq(XO, gameMatchUpdated.getGameBoard().getGameBoard(), playerName).isEmpty()) {
            return gameVerifyBoardService.naSemiCruzDirEsq(XO, gameMatchUpdated.getGameBoard().getGameBoard(), playerName);
        }
        if (!gameVerifyBoardService.naSemiCruzEsqDir(XO, gameMatchUpdated.getGameBoard().getGameBoard(), playerName).isEmpty()) {
            return gameVerifyBoardService.naSemiCruzEsqDir(XO, gameMatchUpdated.getGameBoard().getGameBoard(), playerName);
        }
        if (!gameVerifyBoardService.temVelha(gameMatchUpdated.getGameBoard().getGameBoard()).isEmpty()) {
            return gameVerifyBoardService.temVelha(gameMatchUpdated.getGameBoard().getGameBoard());
        }
        return null;
    }

    public void handleClosingGameRoom(String codeRoom) {
        listGameRooms.forEach((gameRoom) -> {
            if (Objects.equals(gameRoom.getCodeRoom(), codeRoom)) {
                gameRoom.setClosed(true);
            }
        });
    }


    public String handleVerifyGameBoard(GameMatch gameMatch, PlayerInMatch playerInMatch) {
        String verifyGameBoard = verifyGameBoard(gameMatch, playerInMatch.getXO(), playerInMatch.getPlayerName());
        if (verifyGameBoard != null && !verifyGameBoard.isEmpty()) gameMatch.setClosed(true);
        return verifyGameBoard;
    }

    public GameMatch getGameMatchUpdated(GameMatch gameMatch, PlayerInMatch playerInMatch, String position) {
        String codeRoom = gameMatch.getGameRoom().getCodeRoom();
        getGameMatchInList(codeRoom).getPlayerInListPlayersById(playerInMatch.getPlayerId()).setXO(playerInMatch.getXO());

        return handleUpdateGameMatch(codeRoom, position, playerInMatch.getXO());
    }
}