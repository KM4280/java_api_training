package fr.lernejo.navy_battle.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.lernejo.navy_battle.game.Game;
import fr.lernejo.navy_battle.game.board.CellConverter;
import fr.lernejo.navy_battle.game.board.CellCoordinates;
import fr.lernejo.navy_battle.game.board.opponent.OpponentBoard;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Client {

    private final Gson gson = new Gson();
    private final Game game;

    public Client(final Game game) {
        this.game = game;
    }

    public void startGame(final String opponentUrl) throws IOException, InterruptedException {
        System.out.println("Client: Starting game with " + opponentUrl);

        final Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", this.game.getSelfId());
        requestBody.put("url", this.game.getSelfUrl());
        requestBody.put("message", "I will crush you!");
        final String requestJson = this.gson.toJson(requestBody);

        final HttpRequest postRequest = HttpRequest.newBuilder()
            .uri(URI.create(opponentUrl + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestJson))
            .build();

        final HttpClient client = HttpClient.newHttpClient();
        final HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 202) {
            throw new IllegalStateException("Server has not accepted start");
        }

        final String responseJson = response.body();
        final JsonObject responseBody = this.gson.fromJson(responseJson, JsonObject.class);
        final String responseId = responseBody.get("id").getAsString();
        final String responseUrl = responseBody.get("url").getAsString();
        final String responseMessage = responseBody.get("message").getAsString();

        game.setOpponentId(responseId);
        game.setOpponentUrl(responseUrl);
        try {
            System.out.println("Message from " + game.getOpponentId().get() + ": " + responseMessage);
        } catch (final InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void fire(final Game game, final CellCoordinates coordinates) throws ExecutionException, IOException, InterruptedException {
        final String opponentUrl = game.getOpponentUrl().get();
        final String cell = new CellConverter().convert(coordinates.getX(), coordinates.getY());
        System.out.println("Client: Firing on " + cell + " with " + opponentUrl);

        final HttpRequest postRequest = HttpRequest.newBuilder()
            .uri(URI.create(opponentUrl + "/api/game/fire?cell=" + URLEncoder.encode(cell, StandardCharsets.UTF_8)))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .GET()
            .build();

        final HttpClient client = HttpClient.newHttpClient();
        final HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IllegalStateException("Server has not accepted fire");
        }

        final String responseJson = response.body();
        final JsonObject responseBody = this.gson.fromJson(responseJson, JsonObject.class);
        final String responseConsequence = responseBody.get("consequence").getAsString();
        switch (responseConsequence) {
            case "miss" -> game.getOpponentBoard().setCell(coordinates, OpponentBoard.Cell.EMPTY);
            case "hit" -> game.getOpponentBoard().setCell(coordinates, OpponentBoard.Cell.HIT);
            case "sunk" -> game.getOpponentBoard().setCell(coordinates, OpponentBoard.Cell.SUNK);
            default -> throw new IllegalStateException("Unknown consequence");
        }
        final boolean responseShipLeft = responseBody.get("shipLeft").getAsBoolean();
        if (!responseShipLeft) {
            System.out.println("I won!");
        }
    }
}
