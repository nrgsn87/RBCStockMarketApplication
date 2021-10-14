package ca.rbc.microservice.repository;

import ca.rbc.microservice.model.StockRecord;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface RbcStocksRepository extends ReactiveCrudRepository<StockRecord, Integer> {

    Flux<StockRecord> findByStock(String stock);
}
