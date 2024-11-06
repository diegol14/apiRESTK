package es.kuiko.api_comunidades.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuración de seguridad para la API mediante Spring Security.
 * Esta clase define políticas de seguridad de encabezados HTTP y CORS
 * para proteger contra ataques comunes, incluyendo MIME sniffing, clickjacking,
 * y control de fuentes de contenido.
 */
@Configuration
public class SecurityConfig {

    /**
     * Configura la cadena de filtros de seguridad (SecurityFilterChain) para
     * aplicar encabezados de seguridad HTTP y CORS en todas las respuestas de la API,
     * y permite el acceso a todos los endpoints de la API sin autenticación.
     *
     * @param http El objeto HttpSecurity para construir la configuración de seguridad.
     * @return SecurityFilterChain configurada con las políticas de encabezados y CORS.
     * @throws Exception si ocurre algún error en la configuración de seguridad.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Deshabilita CSRF para permitir solicitudes sin token CSRF, adecuado para APIs públicas sin autenticación.
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // Configura CORS
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .anyRequest().permitAll()  // Permite todas las solicitudes sin autenticación, ideal para APIs públicas
            )
            .headers(headers -> headers
                .contentTypeOptions(contentTypeOptions -> contentTypeOptions.disable())  // Evita el MIME sniffing
                .frameOptions(frameOptions -> frameOptions.deny())  // Previene ataques de clickjacking
                .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'"))  // Limita el contenido a tu dominio
            );

        return http.build();
    }

    /**
     * Configura las reglas de CORS para permitir solicitudes desde cualquier origen.
     * Esto es adecuado para una API pública sin autenticación, accesible desde cualquier lugar.
     *
     * @return CorsConfigurationSource con las reglas de CORS aplicadas.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));  // Permite solicitudes desde cualquier origen
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));  // Métodos HTTP permitidos
        configuration.setAllowedHeaders(List.of("*"));  // Permitir todos los encabezados
        configuration.setAllowCredentials(false);  // No se requieren cookies o autenticación basada en sesiones
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // Aplica CORS a todos los endpoints
        return source;
    }
}

