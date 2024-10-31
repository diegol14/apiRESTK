package es.kuiko.api_comunidades.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import es.kuiko.api_comunidades.dto.ProvinciaInfoComunidadDTO;
import es.kuiko.api_comunidades.model.Provincia;
import es.kuiko.api_comunidades.repository.ComunidadAutonomaRepository;
import es.kuiko.api_comunidades.repository.ProvinciaRepository;
import es.kuiko.api_comunidades.service.ProvinciaComunidadInfoService;

@Service
@Validated
public class ProvinciaComunidadInfoServiceImpl implements ProvinciaComunidadInfoService {
	
    private final ProvinciaRepository provinciaRepository;
    private final ComunidadAutonomaRepository comunidadAutonomaRepository;

    public ProvinciaComunidadInfoServiceImpl(ProvinciaRepository provinciaRepository, ComunidadAutonomaRepository comunidadAutonomaRepository) {
        this.provinciaRepository = provinciaRepository;
        this.comunidadAutonomaRepository = comunidadAutonomaRepository;
    }

	@Override
	public Optional<ProvinciaInfoComunidadDTO> findProvinciaComunidadInfoById(Integer codigoProvincia) {
		 validateCodigoProvincia(codigoProvincia);
		 Optional<Provincia> optionalProvincia = provinciaRepository.findProvinciaComunidadInfoById(codigoProvincia);
	        return optionalProvincia.filter(provincia -> provincia.getComunidadAutonoma() != null)
	        		.map(provincia -> new ProvinciaInfoComunidadDTO(
	                provincia.getCodigoProvincia(),
	                provincia.getNombreProvincia(),
	                provincia.getComunidadAutonoma().getCodigoCa(),
	                provincia.getComunidadAutonoma().getNombreCa()
	                ));
	}
	
	// Métodos internos de validación para evitar duplicación (DRY)
    private void validateCodigoProvincia(Integer codigoProvincia) {
        if (codigoProvincia == null || codigoProvincia < 1) {
            throw new IllegalArgumentException("El código de la Provincia no puede ser nulo y debe ser un número positivo");
        }
    }
}
