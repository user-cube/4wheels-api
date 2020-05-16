package pt.ua.tqs.fourwheels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "pt.ua.tqs.fourwheels")
public class FourWheelsApplication {
    public static void main(String[] args) {
        SpringApplication.run(FourWheelsApplication.class);
    }

}