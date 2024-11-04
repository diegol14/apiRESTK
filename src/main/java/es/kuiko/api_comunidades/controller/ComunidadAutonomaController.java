package es.kuiko.api_comunidades.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.kuiko.api_comunidades.dto.ComunidadAutonomaCountProvinciasDTO;
import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import es.kuiko.api_comunidades.service.ComunidadAutonomaService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-kuiko/comunidades-autonomas")
public class ComunidadAutonomaController {

    private final ComunidadAutonomaService comunidadAutonomaService;

    public ComunidadAutonomaController(ComunidadAutonomaService comunidadAutonomaService) {
        this.comunidadAutonomaService = comunidadAutonomaService;
    }
    
    @GetMapping("/")
    public ResponseEntity<List<ComunidadAutonoma>> getAll() {
        List<ComunidadAutonoma> comunidades = comunidadAutonomaService.getAll();
        return ResponseEntity.ok(comunidades);
    }

    @GetMapping("/{caCode}")
    public ResponseEntity<?> getByCode(@PathVariable("caCode") String caCode) {
        Optional<ComunidadAutonoma> comunidad = comunidadAutonomaService.getById(caCode);
        return comunidad.isPresent() 
                ? ResponseEntity.ok(comunidad.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comunidad Autónoma no encontrada");
    }
    
    @GetMapping("/{codigoCa}/cantidad-provincias")
    public ResponseEntity<ComunidadAutonomaCountProvinciasDTO> getCantidadProvincias(@PathVariable String codigoCa) {
        Optional<ComunidadAutonomaCountProvinciasDTO> dto = comunidadAutonomaService.getCantidadProvinciasByComunidad(codigoCa);
        return dto.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody ComunidadAutonoma comunidadAutonoma) {
        ComunidadAutonoma createdComunidad = comunidadAutonomaService.create(comunidadAutonoma);
        return new ResponseEntity<>(createdComunidad, HttpStatus.CREATED);
    }

    @PutMapping("/{caCode}")
    public ResponseEntity<?> update(
        @PathVariable("caCode") String caCode,
        @Valid @RequestBody ComunidadAutonoma comunidadActualizada) {

        ComunidadAutonoma updatedComunidad = comunidadAutonomaService.update(caCode, comunidadActualizada);
        if (updatedComunidad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comunidad Autónoma no encontrada");
        }

        return ResponseEntity.ok(updatedComunidad);
    }

    @DeleteMapping("/{caCode}")
    public ResponseEntity<?> delete(@PathVariable("caCode") String caCode) {
        try {
            comunidadAutonomaService.delete(caCode);
            return ResponseEntity.noContent().build();  // 204 No Content si se elimina exitosamente
        } catch (RuntimeException e) {  // Captura la excepción si la comunidad no existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comunidad Autónoma no encontrada");
        }
    }
}

