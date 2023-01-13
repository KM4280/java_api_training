package fr.lernejo.navy_battle.api.routes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fr.lernejo.navy_battle.api.ApiHandler;
import fr.lernejo.navy_battle.api.ApiResponse;
import fr.lernejo.navy_battle.game.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ApiGameStartRoute implements ApiHandler {

    private final Game game;
    private final Runnable onReadyToFire;

    public ApiGameStartRoute(final Game game, final Runnable onReadyToFire) {
        this.game = game;
        this.onReadyToFire = onReadyToFire;
    }

    @Override
    public ApiResponse handle(final String method, final Map<String, String> queryParams, final JsonElement requestBodyElement) {
        if (method.equals("POST")) {
            if (!requestBodyElement.isJsonObject()) {
                return new ApiResponse(400, "Bad Request: Body is not an object");
            }
            final JsonObject requestBody = requestBodyElement.getAsJsonObject();

            // get id
            if (!requestBody.has("id")) {
                return new ApiResponse(400, "Bad Request: Body has no \"id\" field");
            }
            final JsonElement requestIdElement = requestBody.get("id");
            if (!requestIdElement.isJsonPrimitive()) {
                return new ApiResponse(400, "Bad Request: Body \"id\" is not primitive");
            }
            final JsonPrimitive requestIdPrimitive = requestIdElement.getAsJsonPrimitive();
            if (!requestIdPrimitive.isString()) {
                return new ApiResponse(400, "Bad Request: Body \"id\" is not string");
            }
            final String requestId = requestIdPrimitive.getAsString();

            // get url
            if (!requestBody.has("url")) {
                return new ApiResponse(400, "Bad Request: Body has no \"url\" field");
            }
            final JsonElement requestUrlElement = requestBody.get("url");
            if (!requestUrlElement.isJsonPrimitive()) {
                return new ApiResponse(400, "Bad Request: Body \"url\" is not primitive");
            }
            final JsonPrimitive requestUrlPrimitive = requestUrlElement.getAsJsonPrimitive();
            if (!requestUrlPrimitive.isString()) {
                return new ApiResponse(400, "Bad Request: Body \"url\" is not string");
            }
            final String requestUrl = requestUrlPrimitive.getAsString();

            // get message
            if (!requestBody.has("message")) {
                return new ApiResponse(400, "Bad Request: Body has no \"message\" field");
            }
            final JsonElement requestMessageElement = requestBody.get("message");
            if (!requestMessageElement.isJsonPrimitive()) {
                return new ApiResponse(400, "Bad Request: Body \"message\" is not primitive");
            }
            final JsonPrimitive requestMessagePrimitive = requestMessageElement.getAsJsonPrimitive();
            if (!requestMessagePrimitive.isString()) {
                return new ApiResponse(400, "Bad Request: Body \"message\" is not string");
            }
            final String requestMessage = requestMessagePrimitive.getAsString();

            // creating game
            game.setOpponentId(requestId);
            game.setOpponentUrl(requestUrl);
            try {
                System.out.println("Message from " + game.getOpponentId().get() + ": " + requestMessage);
            } catch (final InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            onReadyToFire.run();

            final Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("id", game.getSelfId());
            responseBody.put("url", this.game.getSelfUrl());
            responseBody.put("message", "May the best code win");
            return new ApiResponse(202, responseBody);
        } else {
            return new ApiResponse(404, "Not Found: Method Not Allowed");
        }
    }
}
