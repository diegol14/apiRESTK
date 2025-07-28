
# API REST para K

Este proyecto es una API REST desarrollada  para **K**. La API proporciona información sobre **Comunidades Autónomas**, **Provincias** y **Gasolineras**, conectándose a una API de terceros para obtener datos actualizados de estaciones de servicio.

## Tabla de Contenidos

- [Características](#características)
- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Uso](#uso)
- [Seguridad y Limitación de Peticiones](#seguridad-y-limitación-de-peticiones)
- [Documentación](#documentación)
- [Contribuciones](#contribuciones)
- [Licencia](#licencia)

## Características

- CRUD completo para Comunidades Autónomas y Provincias.
- Integración con una API de terceros para obtener datos de gasolineras.
- Seguridad básica implementada con configuraciones de encabezados HTTP.
- Limitación de tasa de peticiones mediante **Resilience4j** para proteger contra posibles abusos.
- Documentación generada con **JavaDocs** y **OpenAPI**.
- Configuración de **CORS** para permitir accesos desde múltiples orígenes.

## Requisitos

- **Java 17** o superior
- **Spring Boot 3.3.5**
- **Maven** para gestionar dependencias
- **H2** como base de datos en memoria para almacenamiento temporal
- **OpenAPI** para especificación de la API

## Instalación

1. **Clona el repositorio**:
   ```bash
   git clone https://github.com/diegol14/apiRESTPruebaTecK.git
   cd apiRESTPruebaTecK
   ```

2. **Configura las propiedades**:
   Asegúrate de que los archivos de configuración en `src/main/resources` están correctos para tu entorno:
   - `application.yml`: Configuración general de la aplicación.
   - `application-dev.yml`: Configuración para el entorno de desarrollo.
   - `application-prod.yml`: Configuración para el entorno de producción.

3. **Compila y ejecuta**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. La API estará disponible en `http://localhost:8088` por defecto.

## Uso

### Endpoints Principales

- **Comunidades Autónomas**: `/api-kuiko/comunidades-autonomas`
  - `GET /`: Lista todas las comunidades autónomas.
  - `POST /`: Crea una nueva comunidad autónoma.

- **Provincias**: `/api-kuiko/provincias`
  - `GET /`: Lista todas las provincias.
  - `GET /{codigoProvincia}`: Obtiene detalles de una provincia específica.

- **Gasolineras**: `/api-kuiko/gasolineras/{provinceCode}`
  - `GET /{provinceCode}`: Obtiene información de las gasolineras en la provincia especificada.

## Seguridad y Limitación de Peticiones

- **Encabezados de Seguridad**: La API incluye configuraciones de encabezados HTTP para evitar ataques comunes como **clickjacking** y **MIME sniffing**.
- **Limitación de Tasa**: Implementada con **Resilience4j** para limitar el número de peticiones a 500 por minuto. Esto previene abusos y protege contra ataques de denegación de servicio (DDoS).
- **CORS**: Configurado para permitir acceso desde orígenes múltiples. Útil para pruebas y uso público de la API.

## Documentación

### Acceso a la Documentación

- **Página de Documentación Principal**: Accede a la documentación completa desde el siguiente enlace, que incluye JavaDocs, OpenAPI y resultados de pruebas. 
  - [Documentación Completa](https://diegol14.github.io/apiRESTPruebaTecK)
- **JavaDocs**: Documentación detallada del código Java en formato HTML. 
  - [JavaDocs](https://diegol14.github.io/apiRESTPruebaTecK/apidocs/index.html)
- **OpenAPI**: La especificación de OpenAPI en formato YAML proporciona una descripción completa de la API y sus endpoints.
  - Vista previa: [Especificación OpenAPI](https://diegol14.github.io/apiRESTPruebaTecK/openapi.html)
- **Resultados de las Pruebas**: Documentación de los resultados de pruebas de Postman en formato JSON.
  - Vista previa: [Resultados de Pruebas](https://diegol14.github.io/apiRESTPruebaTecK/test-results.html)

### Colecciones de Postman

Para probar la API, hemos incluido colecciones de Postman en el repositorio en la carpeta raíz. Descárgalas para realizar pruebas en tu entorno local.

## Contribuciones

Las contribuciones son bienvenidas. Si deseas mejorar la funcionalidad o realizar correcciones, abre un **pull request**. Asegúrate de seguir los estándares de código y documentar tus cambios.

## Licencia

Este proyecto es de uso privado y está bajo los términos de la prueba técnica. Para más detalles, revisa el archivo [LICENSE](LICENSE).
