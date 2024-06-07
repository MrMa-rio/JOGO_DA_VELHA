package src.main.shooter;

import src.main.shooter.net.Client;
import src.main.shooter.net.Server;

import java.io.IOException;

public class SinglePlayerRunner {
    public static void main(final String[] args) throws IOException {
        //Server.main(args);
        Client.main(args);
    }
}
