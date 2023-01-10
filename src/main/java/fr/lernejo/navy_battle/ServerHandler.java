package fr.lernejo.navy_battle;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class ServerHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        String response = "OK"; exchange.sendResponseHeaders(200, response.getBytes().length); OutputStream os = exchange.getResponseBody(); os.write(response.getBytes()); os.close();
    }
}
