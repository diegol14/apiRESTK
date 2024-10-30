package es.kuiko.api_comunidades.service;

import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import java.util.List;
import java.util.Optional;

public interface ComunidadAutonomaService {
	
    List<ComunidadAutonoma> findAll();
    
    Optional<ComunidadAutonoma> findById(Long id);
    
    ComunidadAutonoma create(ComunidadAutonoma comunidadAutonoma);
    
    ComunidadAutonoma update(Long id, ComunidadAutonoma comunidadAutonoma);
    
    void delete(Long id);
}