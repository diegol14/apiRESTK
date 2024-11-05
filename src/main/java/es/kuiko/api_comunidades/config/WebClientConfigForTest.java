package es.kuiko.api_comunidades.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfigForTest {

    @Bean
    public WebClient.Builder webClientBuilder() {
        // Devuelve un builder que podemos mockear en los tests
        return WebClient.builder().baseUrl("http://localhost");
    }

    @Bean(name = "testWebClient")
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        // Utiliza el builder para construir el WebClient en el contexto de prueba
        return webClientBuilder.build();
    }
}

