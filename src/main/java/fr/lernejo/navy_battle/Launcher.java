package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.api.Api;
import fr.lernejo.navy_battle.api.routes.ApiGameFireRoute;
import fr.lernejo.navy_battle.api.routes.ApiGameStartRoute;
import fr.lernejo.navy_battle.api.routes.NotFoundRoute;
import fr.lernejo.navy_battle.api.routes.PingRoute;
import fr.lernejo.navy_battle.client.Client;
import fr.lernejo.navy_battle.game.Game;
import fr.lernejo.navy_battle.game.board.CellCoordinates;
import fr.lernejo.navy_battle.game.strategy.ComputerPlayer;
import fr.lernejo.navy_battle.game.strategy.Player;

import java.io.IOException;
import java.util.UUID;

public class Launcher {
    public static void main(final String[] args) {
        final long t0 = System.currentTimeMillis();

        // check if port argument present
        if (args.length < 1) {
            System.err.println("Missing port");
            System.exit(1);
            return;
        }
        // parse port argument
        final int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (final NumberFormatException e) {
            System.err.println("Port must be a number");
            System.exit(1);
            return;
        }

        // check if too much arguments
        if (args.length > 2) {
            System.err.println("Too much arguments");
            System.exit(1);
            return;
        }

        final String selfId = UUID.randomUUID().toString();
        final String selfUrl = "http://" + "localhost" + ":" + port;
        final Player player = new ComputerPlayer();
        final Game game = new Game(selfId, selfUrl, player, true);

        // create client
        final Client client = new Client(game);
        final Runnable onReadyToFire = () -> {
            new Thread(() -> {
                try {
                    final CellCoordinates coordinates = player.giveCoordinates(game.getOpponentBoard());
                    client.fire(game, coordinates);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }).start();
        };

        // create api
        final Api api;
        try {
            api = new Api(port);
        } catch (final IOException e) {
            System.err.println("Error on API Start"); e.printStackTrace(); System.exit(2);
            return;
        }
        api.createContext("/", new NotFoundRoute());
        api.createContext("/ping", new PingRoute());
        api.createContext("/api/game/start", new ApiGameStartRoute(game, onReadyToFire));
        api.createContext("/api/game/fire", new ApiGameFireRoute(game, onReadyToFire));
        System.out.println("Listening on " + selfUrl);

        final long t1 = System.currentTimeMillis();
        final long ms = t1 - t0;
        System.out.println("Finished startup in " + ms + "ms");

        if (args.length > 1) {
            final String opponentUrl = args[1];
            try {
                client.startGame(opponentUrl);
            } catch (final IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}