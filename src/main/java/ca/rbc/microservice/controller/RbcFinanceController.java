package ca.rbc.microservice.controller;

import ca.rbc.microservice.model.StockRecord;
import ca.rbc.microservice.repository.RbcStocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class RbcFinanceController {

    @Autowired
    RbcStocksRepository repository;

    public RbcFinanceController(RbcStocksRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/records/{stock}")
        public Flux<StockRecord> getByStock(@PathVariable("stock") String stock)   {
        return repository.findByStock(stock);
    }

    @PostMapping("/records")
    public Flux<StockRecord> addBulkStockRecord() {

        BufferedReader reader;
        ArrayList<StockRecord> array = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(
                    "src/main/resources/dow_jones_index.data"));
            reader.readLine();
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                String[] stockRecord = line.split(",");
                array.add(new StockRecord(stockRecord[0], stockRecord[1], stockRecord[2]));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    return repository.saveAll(Flux.fromStream(array.stream()));

    }

    @GetMapping("/records")
    public Flux<StockRecord> getAll() {
        return repository.findAll();
    }

}
