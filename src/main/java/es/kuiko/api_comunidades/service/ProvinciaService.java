package es.kuiko.api_comunidades.service;

import es.kuiko.api_comunidades.model.Provincia;
import java.util.List;
import java.util.Optional;

public interface ProvinciaService {
	
    List<Provincia> findAll();
    
    Optional<Provincia> findById(Integer codigoProvincia);
    
    Provincia create(Provincia provincia);
    
    Provincia update(Integer codigoProvincia, Provincia provincia);
    
    void delete(Integer codigoProvincia);
}
