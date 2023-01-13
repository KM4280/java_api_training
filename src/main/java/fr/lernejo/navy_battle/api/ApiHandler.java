package fr.lernejo.navy_battle.api;

import com.google.gson.JsonElement;

import java.util.Map;

public interface ApiHandler {

    ApiResponse handle(final String method, final Map<String, String> queryParams, final JsonElement requestBodyElement);
}
