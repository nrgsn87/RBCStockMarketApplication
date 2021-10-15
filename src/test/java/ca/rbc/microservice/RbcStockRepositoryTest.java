package ca.rbc.microservice;

import ca.rbc.microservice.model.StockRecord;
import ca.rbc.microservice.repository.RbcStocksRepository;
import ca.rbc.microservice.service.StockService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class RbcStockRepositoryTest {

    private WebTestClient webTestClient;

    @Mock
    private RbcStocksRepository repo;

    @Mock
    private StockService service;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .defaultHeader("Accept", "application/json")
                .build();
    }



    @Test
    public void addRecord() {

        webTestClient.post()
                .uri("api/record")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(new StockRecord("4", "test", "1/7/2022")), StockRecord.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.quarter").isEqualTo("4")
                .jsonPath("$.stock").isEqualTo("test")
                .jsonPath("$.date").isEqualTo("1/7/2022");

    }

    @Test
    public void getRecordByStockIndex() {
//        Mockito.when(repo.findByStock("AA"))
//                .thenReturn(Arrays.asList(new StockRecord("4", "AA", "1/7/2021"),
//                        new StockRecord("4", "AA", "1/7/2023"),
//                        new StockRecord("4", "AA", "1/7/2019")));

        webTestClient.get()
                .uri("api/records/AA")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(StockRecord.class).consumeWith(response -> Assertions.assertThat(response.getResponseBody()).hasSize(10));
    }

    @Test
    public void saveAllRecords() {

        webTestClient.post()
                .uri("api/records")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(StockRecord.class).consumeWith(response -> Assertions.assertThat(response.getResponseBody()).hasSize(750));
    }

}
