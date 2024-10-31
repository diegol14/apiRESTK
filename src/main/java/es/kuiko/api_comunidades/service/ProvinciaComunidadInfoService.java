package es.kuiko.api_comunidades.service;

import es.kuiko.api_comunidades.model.Provincia;
import java.util.List;
import java.util.Optional;
import es.kuiko.api_comunidades.dto.ProvinciaInfoComunidadDTO;

public interface ProvinciaComunidadInfoService {

	Optional<ProvinciaInfoComunidadDTO> findProvinciaComunidadInfoById(Integer codigoProvincia);
}
