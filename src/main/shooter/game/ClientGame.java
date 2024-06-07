package src.main.shooter.game;

import src.main.shooter.game.action.SendMessage;
import src.main.shooter.net.Client;

public class ClientGame {
    private final String playerId;
    private final Client client;
    private final SendMessage SendMessage;


    public ClientGame(final Client client, final String clientId) {
        this.client = client;
        playerId = clientId;
        SendMessage = new SendMessage("AQUI TEREMOS TESTE DE MENSAGEM");
    }

    public SendMessage getSendMessage() {
        return SendMessage;
    }


    public void tick() {
        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        client.disconnect(); //TALVEZ GERE UNS BUGS MALUCO
    }
}
