package ca.rbc.microservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table
@Data
@AllArgsConstructor
public class StockRecord {

    @Id
    private Integer id;
    private String quarter;
    private String stock;
    private String date;


    public StockRecord(String quarter, String stock, String date) {
        this.stock = stock;
        this.quarter = quarter;
        this.date = date;
    }

    public StockRecord() {
    }

}