package es.kuiko.api_comunidades.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    @Autowired
    public ComunidadAutonomaController(ComunidadAutonomaServiceImpl comunidadAutonomaServiceImpl) {
        this.comunidadAutonomaServiceImpl = comunidadAutonomaServiceImpl;
    }

    // Método privado para validar y manejar errores
    private ResponseEntity<?> validate(BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        return null; // Si no hay errores, devuelve null
    }

    @PostMapping("/")
    public ResponseEntity<?> createComunidad(@Valid @RequestBody ComunidadAutonoma comunidadAutonoma, BindingResult result) {
        ResponseEntity<?> errors = validate(result);
        if (errors != null) return errors;
        
        ComunidadAutonoma createdComunidad = comunidadAutonomaServiceImpl.create(comunidadAutonoma);
        return new ResponseEntity<>(createdComunidad, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ComunidadAutonoma>> getAllComunidades() {
        List<ComunidadAutonoma> comunidades = comunidadAutonomaServiceImpl.findAll();
        return ResponseEntity.ok(comunidades);
    }

    @GetMapping("/{caCode}")
    public ResponseEntity<?> getComunidadByCode(@PathVariable("caCode") String caCode) {
        Optional<ComunidadAutonoma> comunidad = comunidadAutonomaServiceImpl.findById(caCode);
        return comunidad.isPresent() 
                ? ResponseEntity.ok(comunidad.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comunidad Autónoma no encontrada");
    }
    @PatchMapping("/{caCode}")
    public ResponseEntity<?> updateComunidad(@PathVariable("caCode") String caCode, @Valid @RequestBody ComunidadAutonoma comunidadActualizada, BindingResult result) {
        ResponseEntity<?> errors = validate(result);
        if (errors != null) return errors;

        ComunidadAutonoma updatedComunidad = comunidadAutonomaServiceImpl.update(caCode, comunidadActualizada);
        return updatedComunidad != null ? ResponseEntity.ok(updatedComunidad) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comunidad Autónoma no encontrada");
    }

    @DeleteMapping("/{caCode}")
    public ResponseEntity<?> deleteComunidad(@PathVariable("caCode") String caCode) {
        try {
            comunidadAutonomaServiceImpl.delete(caCode);
            return ResponseEntity.noContent().build();  // 204 No Content si se elimina exitosamente
        } catch (RuntimeException e) {  // Captura la excepción si la comunidad no existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comunidad Autónoma no encontrada");
        }
    }
    
    // Métodos internos de validacion para evitar duplicación (DRY)
    private void validateCodigoCa(String codigoCa) {
        if (codigoCa == null || codigoCa.isBlank()) {
            throw new IllegalArgumentException("El código de la Comunidad no puede ser nulo ni estar vacío");
        }
        if (!codigoCa.matches("[A-Za-z0-9]+")) {
            throw new IllegalArgumentException("El código de la Comunidad contiene caracteres no válidos");
        }
    }
}
