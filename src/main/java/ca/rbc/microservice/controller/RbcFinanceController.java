package ca.rbc.microservice.controller;

import ca.rbc.microservice.model.StockRecord;
import ca.rbc.microservice.repository.RbcStocksRepository;
import ca.rbc.microservice.service.StockService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api")
public class RbcFinanceController {

    private static final Logger LOGGER = getLogger(RbcFinanceController.class);

    @Autowired
    private StockService service;

    public RbcFinanceController(StockService service) {
        this.service = service;
    }

    @GetMapping("/records/{stock}")
        public Flux<StockRecord> getRecordsByStock(@PathVariable("stock") String stock)   {
        return Mono.just(">>Retrieve Records By Stock Index" + stock + "<<")
                .doOnEach(message -> LOGGER.info(message.get()))
                .flatMapMany(res -> service.getRecordsByStockIndex(stock));
    }


    @PostMapping("/record")
    Mono<StockRecord> addStockRecord(@RequestBody StockRecord stockRecord) {
        return
                Mono.just(">>Add new Stock Record<<")
                        .doOnEach(message -> LOGGER.info(message.get())).
                        then(service.addRecord(stockRecord));
    }

    @PostMapping("/records")
    public Flux<StockRecord> addBulkStockRecords() {
        return Mono.just(">>Save Bulk of Records<<")
                .doOnEach(message -> LOGGER.info(message.get()))
                .flatMapMany(res -> service.saveAllRecords());
    }

    @GetMapping("/records")
    public Flux<StockRecord> getAll() {
        return Mono.just(">>Get All Records<<")
                .doOnEach(message -> LOGGER.info(message.get()))
                .flatMapMany(res ->service.findAllRecord());
    }

}
