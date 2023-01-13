package fr.lernejo.navy_battle.api.routes;

import com.google.gson.JsonElement;
import fr.lernejo.navy_battle.api.ApiHandler;
import fr.lernejo.navy_battle.api.ApiResponse;
import fr.lernejo.navy_battle.game.Game;
import fr.lernejo.navy_battle.game.board.CellConverter;
import fr.lernejo.navy_battle.game.board.CellCoordinates;
import fr.lernejo.navy_battle.game.board.self.SelfBoard;

import java.util.HashMap;
import java.util.Map;

public class ApiGameFireRoute implements ApiHandler {

    private final Game game;
    private final Runnable onReadyToFire;

    public ApiGameFireRoute(final Game game, final Runnable onReadyToFire) {
        this.game = game;
        this.onReadyToFire = onReadyToFire;
    }

    @Override
    public ApiResponse handle(final String method, final Map<String, String> queryParams, final JsonElement requestBodyElement) {
        if (method.equals("GET")) {
            // parsing cell and coordinates
            final String cell = queryParams.get("cell");
            if (cell == null) {
                return new ApiResponse(400, "Bad Request: Missing \"cell\" query parameter");
            }
            final CellCoordinates coordinates;
            try {
                coordinates = new CellConverter().convert(cell);
            } catch (final IllegalArgumentException e) {
                return new ApiResponse(400, "Bad Request: Invalid cell");
            }

            // firing
            final SelfBoard.Fire result = game.getSelfBoard().fire(coordinates);

            // getting consequences
            final String consequence = switch (result) {
                case MISS -> "miss";
                case HIT -> "hit";
                case SUNK -> "sunk";
            };
            System.out.println("Server: Fire on " + cell + ": " + consequence);
            final boolean shipLeft = game.getSelfBoard().isValid();
            if (shipLeft) {
                onReadyToFire.run();
            } else {
                System.out.println("I lost!");
            }

            // sending body
            final Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("consequence", consequence);
            responseBody.put("shipLeft", shipLeft);
            return new ApiResponse(200, responseBody);
        } else {
            return new ApiResponse(404, "Not Found: Method Not Allowed");
        }
    }
}
