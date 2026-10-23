import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class App {

    public static void main(String[] args) throws IOException {

        int port = Integer.parseInt(
            System.getenv().getOrDefault("PORT", "8080")
        );

        HttpServer server = HttpServer.create(
            new InetSocketAddress("0.0.0.0", port), 0
        );

        server.createContext("/", (HttpExchange exchange) -> {
            String response =
                "Java CI/CD Application Started Successfully!\n" +
                "Hello from Java CI/CD Pipeline!\n" +
                "Application is running inside Kubernetes.\n";

            byte[] responseBytes =
                response.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders()
                .set("Content-Type", "text/plain; charset=UTF-8");

            exchange.sendResponseHeaders(200, responseBytes.length);

            try (OutputStream output = exchange.getResponseBody()) {
                output.write(responseBytes);
            }
        });

        server.start();

        System.out.println(
            "Java HTTP server started on port " + port
        );
    }
}
