package ma.mundiapolis.springmvc;

import ma.mundiapolis.springmvc.entities.Product;
import ma.mundiapolis.springmvc.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMvcApplication.class, args);
    }

    @Bean
    public CommandLineRunner start(ProductRepository productRepository) {
        return args -> {
            productRepository.save(Product.builder()
                    .name("Computer")
                    .price(5400)
                    .quantity(12)
                    .build());
            productRepository.save(Product.builder()
                    .name("Printer")
                    .price(1200)
                    .quantity(11)
                    .build());
            productRepository.save(Product.builder()
                    .name("Smartphone")
                    .price(12000)
                    .quantity(33)
                    .build());
            productRepository.findAll().forEach(p -> {
                System.out.println(p.toString());
            });
        };
    };
}



