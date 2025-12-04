package wsb.par.graphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GraphqlJavaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlJavaServiceApplication.class, args);
    }

    // Konfiguracja CORS, aby zezwolić na żądania z Twojego klienta
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/graphql").allowedOrigins("http://127.0.0.1:8000", "http://localhost:8000");
            }
        };
    }
}