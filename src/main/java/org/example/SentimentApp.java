package org.example;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SentimentApp {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Эндпоинт /api/sentiment
        server.createContext("/api/sentiment", exchange -> {
            String query = exchange.getRequestURI().getQuery();
            String response = "{\"sentiment\": \"neutral\", \"confidence\": 0.5}"; // Экранированы кавычки для JSON-строки

            if (query != null && query.contains("text=")) {
                // Простейшая Mock-логика (эмуляция ИИ)
                if (query.contains("good") || query.contains("awesome")) {
                    response = "{\"sentiment\": \"positive\", \"confidence\": 0.95}";
                } else if (query.contains("bad") || query.contains("terrible")) {
                    response = "{\"sentiment\": \"negative\", \"confidence\": 0.95}";
                }
            }

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        server.setExecutor(null);
        System.out.println("Server started on port " + port);
        server.start();
    }
}
