package es.kuiko.api_comunidades.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import es.kuiko.api_comunidades.dto.ProvinciaDTO;
import es.kuiko.api_comunidades.dto.ProvinciaInfoComunidadDTO;
import es.kuiko.api_comunidades.exception.CustomNotFoundException;
import es.kuiko.api_comunidades.exception.IllegalUpdateException;
import es.kuiko.api_comunidades.model.Provincia;
import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import es.kuiko.api_comunidades.repository.ComunidadAutonomaRepository;
import es.kuiko.api_comunidades.repository.ProvinciaRepository;

@Service
@Validated
public class ProvinciaServiceImpl {

    private final ProvinciaRepository provinciaRepository;
    private final ComunidadAutonomaRepository comunidadAutonomaRepository;

    public ProvinciaServiceImpl(ProvinciaRepository provinciaRepository, ComunidadAutonomaRepository comunidadAutonomaRepository) {
        this.provinciaRepository = provinciaRepository;
        this.comunidadAutonomaRepository = comunidadAutonomaRepository;
    }

    public List<Provincia> findAll() {
        return provinciaRepository.findAll();
    }

    public Optional<Provincia> findById(Integer codigoProvincia) {
        validateCodigoProvincia(codigoProvincia);
        return provinciaRepository.findById(codigoProvincia);
    }
    
    public Provincia create(ProvinciaDTO provinciaDTO) {
        validateCodigoProvincia(provinciaDTO.getCodigoProvincia());
        ComunidadAutonoma comunidadAutonoma = fetchComunidadByCodigoCa(provinciaDTO.getCodigoCa());
        checkProvinciaDoesNotExist(provinciaDTO.getCodigoProvincia());

        Provincia provincia = new Provincia();
        provincia.setCodigoProvincia(provinciaDTO.getCodigoProvincia());
        provincia.setNombreProvincia(provinciaDTO.getNombreProvincia());
        provincia.setComunidadAutonoma(comunidadAutonoma);

        return provinciaRepository.save(provincia);
    }

    public Provincia update(Integer codigoProvincia, ProvinciaDTO provinciaModificadaDTO) {
        validateCodigoProvincia(codigoProvincia);
        validateCodigoProvincia(provinciaModificadaDTO.getCodigoProvincia());

        Provincia existingProvincia = provinciaRepository.findById(codigoProvincia)
            .orElseThrow(() -> new CustomNotFoundException("Provincia con código " + codigoProvincia + " no encontrada"));

        checkComunidadNotChanged(existingProvincia, provinciaModificadaDTO);

        // Actualiza solo el nombre; la Comunidad Autónoma no se cambia
        existingProvincia.setNombreProvincia(provinciaModificadaDTO.getNombreProvincia());

        return provinciaRepository.save(existingProvincia);
    }

    public void delete(Integer codigoProvincia) {
        validateCodigoProvincia(codigoProvincia);
        ensureProvinciaExists(codigoProvincia);
        provinciaRepository.deleteById(codigoProvincia);
    }
    
    // Métodos internos de validación para evitar duplicación (DRY)
    
    private void validateCodigoProvincia(Integer codigoProvincia) {
        if (codigoProvincia == null || codigoProvincia < 1) {
            throw new IllegalArgumentException("El código de la Provincia no puede ser nulo y debe ser un número positivo");
        }
    }

    private void ensureProvinciaExists(Integer codigoProvincia) {
        if (!provinciaRepository.existsById(codigoProvincia)) {
            throw new CustomNotFoundException("Provincia con código " + codigoProvincia + " no encontrada");
        }
    }

    private ComunidadAutonoma fetchComunidadByCodigoCa(String codigoCa) {
        if (codigoCa == null || codigoCa.isBlank()) {
            throw new IllegalArgumentException("El código de la Comunidad Autónoma no puede ser nulo ni estar vacío");
        }
        return comunidadAutonomaRepository.findById(codigoCa)
            .orElseThrow(() -> new CustomNotFoundException("ComunidadAutonoma con código " + codigoCa + " no encontrada"));
    }

    private void checkProvinciaDoesNotExist(Integer codigoProvincia) {
        if (provinciaRepository.existsById(codigoProvincia)) {
            throw new IllegalArgumentException("Ya existe una Provincia con el código " + codigoProvincia);
        }
    }

    private void checkComunidadNotChanged(Provincia existingProvincia, ProvinciaDTO provinciaModificadaDTO) {
        if (provinciaModificadaDTO.getCodigoCa() != null &&
            !existingProvincia.getComunidadAutonoma().getCodigoCa().equals(provinciaModificadaDTO.getCodigoCa())) {
            throw new IllegalUpdateException("No se permite actualizar el código de la comunidad autónoma");
        }
    }



}

