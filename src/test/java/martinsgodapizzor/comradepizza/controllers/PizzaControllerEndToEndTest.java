package martinsgodapizzor.comradepizza.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PizzaControllerEndToEndTest {

    @LocalServerPort
    private int port;

    @Test
    void getPizzasReturnsListOfPizzas() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/comrade"))
                .build();

        var response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .join();

        assertThat(response.statusCode()).isEqualTo(200);
    }

}
