package src.main.jogo.models;

import java.io.Serializable;

public record Player(String playerId, String playerName) implements Serializable {
}
