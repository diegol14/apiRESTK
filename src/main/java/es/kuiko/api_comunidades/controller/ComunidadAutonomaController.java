package es.kuiko.api_comunidades.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import es.kuiko.api_comunidades.service.impl.ComunidadAutonomaServiceImpl;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-kuiko/comunidades-autonomas")
public class ComunidadAutonomaController {

    private final ComunidadAutonomaServiceImpl comunidadAutonomaServiceImpl;

    public ComunidadAutonomaController(ComunidadAutonomaServiceImpl comunidadAutonomaServiceImpl) {
        this.comunidadAutonomaServiceImpl = comunidadAutonomaServiceImpl;
    }
    
    @GetMapping("/")
    public ResponseEntity<List<ComunidadAutonoma>> getAll() {
        List<ComunidadAutonoma> comunidades = comunidadAutonomaServiceImpl.findAll();
        return ResponseEntity.ok(comunidades);
    }

    @GetMapping("/{caCode}")
    public ResponseEntity<?> getByCode(@PathVariable("caCode") String caCode) {
        Optional<ComunidadAutonoma> comunidad = comunidadAutonomaServiceImpl.findById(caCode);
        return comunidad.isPresent() 
                ? ResponseEntity.ok(comunidad.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comunidad Autónoma no encontrada");
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody ComunidadAutonoma comunidadAutonoma) {
        ComunidadAutonoma createdComunidad = comunidadAutonomaServiceImpl.create(comunidadAutonoma);
        return new ResponseEntity<>(createdComunidad, HttpStatus.CREATED);
    }

    @PatchMapping("/{caCode}")
    public ResponseEntity<?> update(
        @PathVariable("caCode") String caCode,
        @Valid @RequestBody ComunidadAutonoma comunidadActualizada) {

        ComunidadAutonoma updatedComunidad = comunidadAutonomaServiceImpl.update(caCode, comunidadActualizada);
        if (updatedComunidad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comunidad Autónoma no encontrada");
        }

        return ResponseEntity.ok(updatedComunidad);
    }

    @DeleteMapping("/{caCode}")
    public ResponseEntity<?> delete(@PathVariable("caCode") String caCode) {
        try {
            comunidadAutonomaServiceImpl.delete(caCode);
            return ResponseEntity.noContent().build();  // 204 No Content si se elimina exitosamente
        } catch (RuntimeException e) {  // Captura la excepción si la comunidad no existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comunidad Autónoma no encontrada");
        }
    }
}

