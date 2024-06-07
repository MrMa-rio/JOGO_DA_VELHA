package src.main.shooter.game;

import java.util.TreeMap;

import src.main.shooter.game.action.ActionSet;
import src.main.shooter.net.Client;

public class ClientGame {
    private final int playerId;
    private final Client client;

    private final ActionSet actionSet;

    //private TreeMap<Integer, Entity> entities; // O que e uma TreeMap

    public ClientGame(final Client client, final int clientId) {
        this.client = client;
        playerId = clientId;
        actionSet = new ActionSet();
    }

    public ActionSet getActionSet() {
        return actionSet;
    }


    public void tick() {

        client.disconnect(); //TALVEZ GERE UNS BUGS MALUCO
    }
}
