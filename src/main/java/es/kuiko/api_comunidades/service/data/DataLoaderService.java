package es.kuiko.api_comunidades.service.data;

import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import es.kuiko.api_comunidades.model.Provincia;
import java.util.List;

public interface DataLoaderService {
	
    List<ComunidadAutonoma> loadComunidades(String filePath); // Cargar comunidades desde archivo CSV
    
    List<Provincia> loadProvincias(String filePath); // Cargar provincias desde archivo CSV
}
