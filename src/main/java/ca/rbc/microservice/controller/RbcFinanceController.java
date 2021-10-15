package ca.rbc.microservice.controller;

import ca.rbc.microservice.model.StockRecord;
import ca.rbc.microservice.repository.RbcStocksRepository;
import ca.rbc.microservice.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class RbcFinanceController {

    @Autowired
    private StockService service;

    public RbcFinanceController(StockService service) {
        this.service = service;
    }

    @GetMapping("/records/{stock}")
        public Flux<StockRecord> getRecordsByStock(@PathVariable("stock") String stock)   {
        return service.getRecordsByStockIndex(stock);
    }

    @PostMapping("/record")
    Mono<StockRecord> addStockRecord(@RequestBody StockRecord stockRecord) {
        return service.addRecord(stockRecord);
    }

    @PostMapping("/records")
    public Flux<StockRecord> addBulkStockRecords() {
            return service.saveAllRecords();
    }

    @GetMapping("/records")
    public Flux<StockRecord> getAll() {
        return service.findAllRecord();
    }

}
