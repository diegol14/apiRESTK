package es.kuiko.api_comunidades.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import es.kuiko.api_comunidades.dto.ProvinciaDTO;
import es.kuiko.api_comunidades.dto.ProvinciaInfoComunidadDTO;
import es.kuiko.api_comunidades.model.Provincia;
import es.kuiko.api_comunidades.service.ProvinciaService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-kuiko/provincias")
@Tag(name = "Provincias", description = "Operaciones relacionadas con las Provincias")
public class ProvinciaController {

    private final ProvinciaService provinciaService;

    public ProvinciaController(ProvinciaService provinciaService) {
        this.provinciaService = provinciaService;
    }
    
    @Operation(summary = "Obtener todas las provincias", 
               description = "Devuelve una lista de todas las provincias registradas.")
    @GetMapping("/")
    public ResponseEntity<List<Provincia>> getAll() {
        List<Provincia> provincias = provinciaService.getAll();
        return ResponseEntity.ok(provincias);
    }

    @Operation(summary = "Obtener una provincia por código", 
               description = "Devuelve los detalles de la provincia que coincide con el código especificado.")
    @GetMapping("/{codigoProvincia}")
    public ResponseEntity<?> getByCodigo(@PathVariable("codigoProvincia") Integer codigoProvincia) {
        validateCodigoProvincia(codigoProvincia);
        Optional<Provincia> provincia = provinciaService.getById(codigoProvincia);
        return provincia.isPresent() 
                ? ResponseEntity.ok(provincia.get()) 
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Provincia no encontrada");
    }
    
    @Operation(summary = "Obtener detalles de una provincia y su comunidad autónoma", 
               description = "Devuelve información adicional sobre la provincia especificada, incluyendo el nombre y código de la comunidad autónoma.")
    @GetMapping("/{codigoProvincia}/detalles-comunidad")
    public ResponseEntity<?> findProvinciaComunidadInfo(@PathVariable("codigoProvincia") Integer codigoProvincia) {
        Optional<ProvinciaInfoComunidadDTO> response = provinciaService.getProvinciaComunidadInfoById(codigoProvincia);
        return response.isPresent() ? ResponseEntity.ok(response.get()) : ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Crear una nueva provincia", 
               description = "Permite registrar una nueva provincia en el sistema.")
    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody ProvinciaDTO provinciaDTO) {
        Provincia created = provinciaService.create(provinciaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Actualizar una provincia existente", 
               description = "Actualiza los detalles de una provincia usando su código.")
    @PutMapping("/{codigoProvincia}")
    public ResponseEntity<?> update(
            @PathVariable("codigoProvincia") Integer codigoProvincia, 
            @Valid @RequestBody ProvinciaDTO provinciaDTOActualizada) {
        validateCodigoProvincia(codigoProvincia);
        Provincia updated = provinciaService.update(codigoProvincia, provinciaDTOActualizada);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Eliminar una provincia", 
               description = "Elimina la provincia especificada por su código.")
    @DeleteMapping("/{codigoProvincia}")
    public ResponseEntity<?> delete(@PathVariable("codigoProvincia") Integer codigoProvincia) {
        validateCodigoProvincia(codigoProvincia);
        try {
            provinciaService.delete(codigoProvincia);
            return ResponseEntity.noContent().build();  // 204 No Content si se elimina exitosamente
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Provincia no encontrada");
        }
    }
    
    // Método de validación para el código de provincia
    private void validateCodigoProvincia(Integer codigoProvincia) {
        if (codigoProvincia == null || codigoProvincia < 1) {
            throw new IllegalArgumentException("El código de la Provincia no puede ser nulo y debe ser un número positivo");
        }
    }
}
