package ir.maleki.sideprojects.nexttick.backend.interfaces.rest.adapters;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class TimeSlotReserveIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void should_reserve_time_slot_when_valid_request() {
        Long userId = 1L;

        webTestClient.post()
                .uri("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                          "holderId": "%s"
                        }
                        """.formatted(userId))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.holderId").isEqualTo(userId);
    }
}
