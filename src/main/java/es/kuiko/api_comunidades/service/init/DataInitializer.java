package es.kuiko.api_comunidades.service.init;

import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import es.kuiko.api_comunidades.model.Provincia;
import es.kuiko.api_comunidades.service.data.DataLoaderService;
import es.kuiko.api_comunidades.service.database.PersistenceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de inicialización de datos para la aplicación. 
 * 
 * <p>
 * La clase {@code DataInitializer} implementa {@link CommandLineRunner}, lo que permite que
 * su método {@code run} se ejecute automáticamente al inicio de la aplicación.
 * Su propósito es cargar datos iniciales de comunidades autónomas y provincias desde archivos CSV
 * y guardarlos en la base de datos.
 * </p>
 * 
 * <p>
 * Los archivos CSV son leídos mediante el servicio {@link DataLoaderService}, y los datos cargados 
 * son persistidos en la base de datos mediante el servicio {@link PersistenceService}.
 * Las rutas de los archivos CSV se especifican en las propiedades de configuración de la aplicación
 * y se inyectan utilizando la anotación {@link Value}.
 * </p>
 */
@Service
public class DataInitializer implements CommandLineRunner {

    private final DataLoaderService dataLoaderService;
    private final PersistenceService persistenceService;

    @Value("${csv.comunidades.path}")
    private String comunidadesCsvPath;

    @Value("${csv.provincias.path}")
    private String provinciasCsvPath;

    /**
     * Constructor para inyectar las dependencias {@link DataLoaderService} y {@link PersistenceService}.
     *
     * @param dataLoaderService Servicio encargado de cargar datos desde archivos CSV.
     * @param persistenceService Servicio encargado de persistir datos en la base de datos.
     */
    public DataInitializer(DataLoaderService dataLoaderService, PersistenceService persistenceService) {
        this.dataLoaderService = dataLoaderService;
        this.persistenceService = persistenceService;
    }

    /**
     * Método ejecutado automáticamente al inicio de la aplicación. 
     * Carga datos de comunidades autónomas y provincias desde archivos CSV y 
     * los persiste en la base de datos.
     * 
     * <p>
     * Este método utiliza el {@link DataLoaderService} para cargar los datos desde
     * los archivos CSV ubicados en las rutas especificadas por las propiedades
     * {@code csv.comunidades.path} y {@code csv.provincias.path}.
     * A continuación, los datos son guardados en la base de datos usando el {@link PersistenceService}.
     * </p>
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    @Override
    public void run(String... args) {
        // Cargar y persistir comunidades autónomas
        List<ComunidadAutonoma> comunidades = dataLoaderService.loadComunidades(comunidadesCsvPath);
        persistenceService.saveComunidades(comunidades);

        // Cargar y persistir provincias
        List<Provincia> provincias = dataLoaderService.loadProvincias(provinciasCsvPath);
        persistenceService.saveProvincias(provincias);
    }
}
