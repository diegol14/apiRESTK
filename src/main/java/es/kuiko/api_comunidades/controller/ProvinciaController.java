package es.kuiko.api_comunidades.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import es.kuiko.api_comunidades.dto.ProvinciaDTO;
import es.kuiko.api_comunidades.model.Provincia;
import es.kuiko.api_comunidades.service.impl.ProvinciaServiceImpl;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-kuiko/provincias")
public class ProvinciaController {

    private final ProvinciaServiceImpl provinciaServiceImpl;

    public ProvinciaController(ProvinciaServiceImpl provinciaServiceImpl) {
        this.provinciaServiceImpl = provinciaServiceImpl;
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Provincia>> getAll() {
        List<Provincia> provincias = provinciaServiceImpl.findAll();
        return ResponseEntity.ok(provincias);
    }

    @GetMapping("/{codigoProvincia}")
    public ResponseEntity<?> getByCodigo(@PathVariable("codigoProvincia") Integer codigoProvincia) {
        Optional<Provincia> provincia = provinciaServiceImpl.findById(codigoProvincia);
        return provincia.isPresent() 
                ? ResponseEntity.ok(provincia.get()) 
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Provincia no encontrada");
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody ProvinciaDTO provinciaDTO) {
        Provincia created = provinciaServiceImpl.create(provinciaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{codigoProvincia}")
    public ResponseEntity<?> update(
            @PathVariable("codigoProvincia") Integer codigoProvincia, 
            @Valid @RequestBody ProvinciaDTO provinciaDTOActualizada) {

        Provincia updated = provinciaServiceImpl.update(codigoProvincia, provinciaDTOActualizada);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{codigoProvincia}")
    public ResponseEntity<?> delete(@PathVariable("caCode") Integer codigoProvincia) {
        try {
            provinciaServiceImpl.delete(codigoProvincia);
            return ResponseEntity.noContent().build();  // 204 No Content si se elimina exitosamente
        } catch (RuntimeException e) {  // Captura la excepción si la comunidad no existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Provincia no encontrada");
        }
    }
}
