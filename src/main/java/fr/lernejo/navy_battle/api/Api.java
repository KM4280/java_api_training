package fr.lernejo.navy_battle.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class Api {

    private final Gson gson = new Gson();
    private final HttpServer server;

    public Api(final int port) throws IOException {
        this.server = HttpServer.create();
        this.server.setExecutor(Executors.newSingleThreadExecutor());
        this.server.bind(new InetSocketAddress(port), 0);
        this.server.start();
    }

    public void createContext(final String path, final ApiHandler handler) {
        this.server.createContext(path, exchange -> {
            int status;
            Object responseBody;
            try {
                // method verb
                final String method = exchange.getRequestMethod();

                // parse query params
                final Map<String, String> queryParams = new HashMap<>();
                final String queryString = exchange.getRequestURI().getRawQuery();
                if (queryString != null) {
                    final String[] splitQueryString = queryString.split("&");
                    for (final String queryParam : splitQueryString) {
                        final String[] splitQueryParam = queryParam.split("=");
                        if (splitQueryParam.length == 1) {
                            final String key = URLDecoder.decode(splitQueryParam[0], StandardCharsets.UTF_8);
                            queryParams.put(key, "");
                        } else if (splitQueryParam.length == 2) {
                            final String key = URLDecoder.decode(splitQueryParam[0], StandardCharsets.UTF_8);
                            final String value = URLDecoder.decode(splitQueryParam[1], StandardCharsets.UTF_8);
                            queryParams.put(key, value);
                        }
                    }
                }

                // parse json body
                final JsonElement requestBody;
                try (final InputStream is = exchange.getRequestBody()) {
                    final Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
                    requestBody = this.gson.fromJson(reader, JsonElement.class);
                }

                // handle
                try {
                    final ApiResponse response = handler.handle(method, queryParams, requestBody);
                    status = response.getStatus();
                    responseBody = response.getBody();

                } catch (final Exception e) {
                    e.printStackTrace();
                    status = 500;
                    responseBody = "Internal Server Error";
                }

            } catch (final JsonSyntaxException e) {
                status = 400;
                responseBody = "Bad Request: Invalid JSON";

            } catch (final Exception e) {
                e.printStackTrace();
                status = 500;
                responseBody = "Internal Server Error";
            }

            final String body;
            if (responseBody instanceof String) {
                body = (String) responseBody;
            } else {
                body = this.gson.toJson(responseBody);
            }
            final byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
            exchange.sendResponseHeaders(status, bytes.length);
            try (final OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        });
    }
}
