package ca.rbc.microservice;


import ca.rbc.microservice.repository.RbcStocksRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.core.DatabaseClient;

@SpringBootApplication
public class StockMarketApplication {
	public static void main(String[] args) {
		SpringApplication.run(StockMarketApplication.class, args);
	}


	@Bean
	ApplicationRunner init(RbcStocksRepository repository, DatabaseClient client) {
		return args -> {

			client.sql("create table IF NOT EXISTS stock_record" +
					"(id SERIAL PRIMARY KEY, " +
					"quarter varchar (255), " +
					"stock varchar (255) not null, " +
					"date varchar (255))").fetch().first().subscribe();
		};
	}
}
