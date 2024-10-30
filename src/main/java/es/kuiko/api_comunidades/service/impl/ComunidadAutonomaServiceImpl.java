package es.kuiko.api_comunidades.service.impl;

import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import es.kuiko.api_comunidades.repository.ComunidadAutonomaRepository;
import es.kuiko.api_comunidades.service.ComunidadAutonomaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComunidadAutonomaServiceImpl implements ComunidadAutonomaService {

    private final ComunidadAutonomaRepository comunidadAutonomaRepository;

    public ComunidadAutonomaServiceImpl(ComunidadAutonomaRepository comunidadAutonomaRepository) {
        this.comunidadAutonomaRepository = comunidadAutonomaRepository;
    }

    @Override
    public List<ComunidadAutonoma> findAll() {
        return comunidadAutonomaRepository.findAll();
    }

    @Override
    public Optional<ComunidadAutonoma> findById(String codigoCa) {
        validateCodigoCa(codigoCa);  
        return comunidadAutonomaRepository.findById(codigoCa);
    }

    @Override
    public ComunidadAutonoma create(ComunidadAutonoma comunidadAutonoma) {
        validateCodigoCa(comunidadAutonoma);
        if (doesComunidadExist(comunidadAutonoma.getCodigoCa())) {
            throw new IllegalArgumentException("Ya existe una ComunidadAutonoma con el código " + comunidadAutonoma.getCodigoCa());
        }
        return comunidadAutonomaRepository.save(comunidadAutonoma);
    }

    @Override
    public ComunidadAutonoma update(String codigoCa, ComunidadAutonoma comunidadAutonoma) {
    	validateCodigoCa(codigoCa);
    	ensureComunidadExists(codigoCa);
        comunidadAutonoma.setCodigoCa(codigoCa);
        return comunidadAutonomaRepository.save(comunidadAutonoma);
    }

    @Override
    public void delete(String codigoCa) {
    	validateCodigoCa(codigoCa);
        ensureComunidadExists(codigoCa);
        comunidadAutonomaRepository.deleteById(codigoCa);
    }

    // Métodos internos de validacion para evitar duplicación (DRY)
    private void validateCodigoCa(ComunidadAutonoma comunidadAutonoma) {
        validateCodigoCa(comunidadAutonoma.getCodigoCa());
    }

    private void validateCodigoCa(String codigoCa) {
        if (codigoCa == null || codigoCa.isBlank()) {
            throw new IllegalArgumentException("El código de la Comunidad no puede ser nulo ni estar vacío");
        }
    }

    private void ensureComunidadExists(String codigoCa) {
        if (!doesComunidadExist(codigoCa)) {
            throw new RuntimeException("Comunidad Autónoma no encontrada");
        }
    }

    private boolean doesComunidadExist(String codigoCa) {
        return comunidadAutonomaRepository.existsById(codigoCa);
    }
}
