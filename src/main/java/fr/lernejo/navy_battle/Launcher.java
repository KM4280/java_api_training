package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class Launcher {
    public static void main(String[] args) throws IOException{
        ServerHandler handle = new ServerHandler();
        int port = 9876;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/test", new ServerHandler());
        server.start();
    }
}