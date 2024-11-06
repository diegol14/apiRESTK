package es.kuiko.api_comunidades.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.SslProvider;

import java.security.KeyStore;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * Configuración de WebClient para realizar solicitudes HTTP seguras
 * a un servidor externo utilizando un certificado SSL personalizado.
 * Esta configuración permite que la aplicación se conecte de manera
 * segura a un servidor que requiere autenticación SSL.
 */
@Configuration
public class WebClientConfig {

    // Inyecta la URL base desde el archivo de configuración
    @Value("${api.base-url}")
    private String baseUrl;

    /**
     * Crea y configura un bean de WebClient con un certificado SSL personalizado.
     * El WebClient se utiliza para realizar solicitudes a la API de precios de carburantes.
     *
     * @return WebClient configurado para conexiones SSL seguras.
     * @throws Exception si ocurre algún error al cargar el certificado o configurar SSL.
     */
    @Bean
    @Primary
    public WebClient webClient() throws Exception {
        // Cargar el certificado desde el directorio resources/certs
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate cert;
        try (InputStream certStream = new ClassPathResource("certs/server.crt").getInputStream()) {
            cert = cf.generateCertificate(certStream);
        }

        // Crear un KeyStore y agregar el certificado al KeyStore
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("server", cert);

        // Crear un TrustManagerFactory que utiliza el KeyStore con el certificado
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);

        // Crear el contexto SSL que utiliza el TrustManagerFactory configurado
        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(tmf)
                .build();

        // Configurar HttpClient con el contexto SSL personalizado
        HttpClient httpClient = HttpClient.create()
            .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));

        // Crear y devolver el WebClient configurado para realizar solicitudes seguras
        return WebClient.builder()
                        .clientConnector(new ReactorClientHttpConnector(httpClient))
                        .baseUrl(baseUrl)  // Utilizar la URL inyectada desde el archivo de configuración
                        .build();
    }
}
