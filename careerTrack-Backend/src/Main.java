import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(
                new InetSocketAddress("localhost", 8080), 0
        );

        server.createContext("/api/health", exchange -> {
            exchange.getResponseHeaders().set(
                    "Access-Control-Allow-Origin",
                    "http://localhost:5173"
            );

            exchange.getResponseHeaders().set(
                    "Content-Type",
                    "application/json; charset=UTF-8"
            );

            byte[] response = """
                {"message":"CareerTrack backend is running"}
                """.getBytes(StandardCharsets.UTF_8);

            exchange.sendResponseHeaders(200, response.length);

            try (var output = exchange.getResponseBody()) {
                output.write(response);
            }
        });

        server.start();
        System.out.println("Backend: http://localhost:8080");
    }
}