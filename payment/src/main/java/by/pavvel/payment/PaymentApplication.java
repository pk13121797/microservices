package by.pavvel.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "by.pavvel.payment",
                "com.example.amqp"
        }
)
public class PaymentApplication {
    public static void main(String[] args) { {
        SpringApplication.run(PaymentApplication.class, args);
    }}
}
