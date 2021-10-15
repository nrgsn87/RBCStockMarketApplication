package ca.rbc.microservice.service;

import ca.rbc.microservice.model.StockRecord;
import ca.rbc.microservice.repository.RbcStocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class StockService {

    @Autowired
    private RbcStocksRepository repo;

    public Flux<StockRecord> getRecordsByStockIndex(String stock) {
        return repo.findByStock(stock);
    }

    public Mono<StockRecord> addRecord(StockRecord stockRecord) {
        return repo.save(stockRecord);
    }

    public Flux<StockRecord> saveAllRecords() {

        ArrayList<StockRecord> array = getStockRecordArrayList();

        return repo.saveAll(Flux.fromStream(array.stream()));
    }

    public Flux<StockRecord> findAllRecord() {
        return repo.findAll();
    }

    private ArrayList<StockRecord> getStockRecordArrayList() {
        BufferedReader reader;
        ArrayList<StockRecord> stockRecords = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(
                    "src/main/resources/dow_jones_index.data"));
            reader.readLine();
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                String[] stockRecord = line.split(",");
                stockRecords.add(new StockRecord(stockRecord[0], stockRecord[1], stockRecord[2]));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stockRecords;
    }

}
