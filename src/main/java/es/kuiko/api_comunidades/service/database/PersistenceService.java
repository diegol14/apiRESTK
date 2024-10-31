package es.kuiko.api_comunidades.service.database;

import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import es.kuiko.api_comunidades.model.Provincia;
import java.util.List;

public interface PersistenceService {
	
    void saveComunidades(List<ComunidadAutonoma> comunidades);
    
    void saveProvincias(List<Provincia> provincias);
}
