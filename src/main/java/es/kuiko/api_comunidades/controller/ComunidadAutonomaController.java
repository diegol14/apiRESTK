package es.kuiko.api_comunidades.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import es.kuiko.api_comunidades.dto.ComunidadAutonomaCountProvinciasDTO;
import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import es.kuiko.api_comunidades.service.ComunidadAutonomaService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-kuiko/comunidades-autonomas")
@Tag(name = "Comunidades Autónomas", description = "Operaciones sobre Comunidades Autónomas")
public class ComunidadAutonomaController {

    private final ComunidadAutonomaService comunidadAutonomaService;

    public ComunidadAutonomaController(ComunidadAutonomaService comunidadAutonomaService) {
        this.comunidadAutonomaService = comunidadAutonomaService;
    }
    
    @Operation(summary = "Obtener todas las Comunidades Autónomas", 
               description = "Devuelve una lista de todas las Comunidades Autónomas registradas.")
    @GetMapping("/")
    public ResponseEntity<List<ComunidadAutonoma>> getAll() {
        List<ComunidadAutonoma> comunidades = comunidadAutonomaService.getAll();
        return ResponseEntity.ok(comunidades);
    }

    @Operation(summary = "Obtener una Comunidad Autónoma por código", 
               description = "Devuelve los detalles de la Comunidad Autónoma que coincide con el código especificado.")
    @GetMapping("/{caCode}")
    public ResponseEntity<?> getByCode(@PathVariable("caCode") String caCode) {
        Optional<ComunidadAutonoma> comunidad = comunidadAutonomaService.getById(caCode);
        return comunidad.isPresent() 
                ? ResponseEntity.ok(comunidad.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comunidad Autónoma no encontrada");
    }
    
    @Operation(summary = "Obtener la cantidad de provincias de una Comunidad Autónoma", 
               description = "Devuelve el número de provincias asociadas a la Comunidad Autónoma especificada.")
    @GetMapping("/{codigoCa}/cantidad-provincias")
    public ResponseEntity<ComunidadAutonomaCountProvinciasDTO> getCantidadProvincias(@PathVariable String codigoCa) {
        Optional<ComunidadAutonomaCountProvinciasDTO> dto = comunidadAutonomaService.getCantidadProvinciasByComunidad(codigoCa);
        return dto.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva Comunidad Autónoma", 
               description = "Permite registrar una nueva Comunidad Autónoma en el sistema.")
    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody ComunidadAutonoma comunidadAutonoma) {
        ComunidadAutonoma createdComunidad = comunidadAutonomaService.create(comunidadAutonoma);
        return new ResponseEntity<>(createdComunidad, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar una Comunidad Autónoma existente", 
               description = "Actualiza los detalles de una Comunidad Autónoma usando su código.")
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

    @Operation(summary = "Eliminar una Comunidad Autónoma", 
               description = "Elimina la Comunidad Autónoma especificada por su código.")
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
