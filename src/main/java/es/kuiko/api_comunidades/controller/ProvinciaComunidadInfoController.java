package es.kuiko.api_comunidades.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.kuiko.api_comunidades.dto.ProvinciaInfoComunidadDTO;
import es.kuiko.api_comunidades.service.impl.ProvinciaComunidadInfoServiceImpl;

@RestController
@RequestMapping("/api-kuiko/provincias")
public class ProvinciaComunidadInfoController {
	
	@Autowired
	private ProvinciaComunidadInfoServiceImpl provinciaComunidadInfoServiceImpl;
	
    //Se agrega endpoint que devuelve provincia, codigo y nombre de ComunidadAutonoma
    @GetMapping("/{codigoProvincia}/detalles-comunidad")
    public ResponseEntity<?> findProvinciaComunidadInfo(@PathVariable("codigoProvincia") Integer codigoProvincia) {
        
    	Optional<ProvinciaInfoComunidadDTO> response = provinciaComunidadInfoServiceImpl.findProvinciaComunidadInfoById(codigoProvincia);
        
        return response.isPresent() ? ResponseEntity.ok(response.get()) : ResponseEntity.notFound().build();
    }

}
