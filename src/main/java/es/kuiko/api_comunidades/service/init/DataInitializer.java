package es.kuiko.api_comunidades.service.init;

import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import es.kuiko.api_comunidades.model.Provincia;
import es.kuiko.api_comunidades.service.data.DataLoaderService;
import es.kuiko.api_comunidades.service.database.PersistenceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataInitializer implements CommandLineRunner {

    private final DataLoaderService dataLoaderService;
    private final PersistenceService persistenceService;

    @Value("${csv.comunidades.path}")
    private String comunidadesCsvPath;

    @Value("${csv.provincias.path}")
    private String provinciasCsvPath;

    public DataInitializer(DataLoaderService dataLoaderService, PersistenceService persistenceService) {
        this.dataLoaderService = dataLoaderService;
        this.persistenceService = persistenceService;
    }

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
