package by.pavvel.shipment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
		scanBasePackages = {
				"by.pavvel.shipment",
				"by.pavvel.payment.config.kafka"
		}
)
@EnableFeignClients(basePackages = "com.example.clients")
public class ShipmentApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShipmentApplication.class, args);
	}
}
