package es.kuiko.api_comunidades.controller;

import es.kuiko.api_comunidades.dto.gasolineras.GasolineraWrapperApiResponseOut;
import es.kuiko.api_comunidades.service.GasolineraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api-kuiko")
@Tag(name = "Gasolineras", description = "Operaciones relacionadas con la información de gasolineras")
public class GasolineraController {

    private final GasolineraService gasolineraService;

    public GasolineraController(GasolineraService gasolineraService) {
        this.gasolineraService = gasolineraService;
    }

    @Operation(
        summary = "Obtener gasolineras por provincia",
        description = "Devuelve una lista de gasolineras para la provincia especificada mediante su código."
    )
    @GetMapping("/gasolineras/{provinceCode}")
    public Mono<ResponseEntity<GasolineraWrapperApiResponseOut>> getGasolinerasPorProvincia(@PathVariable String provinceCode) {
        return gasolineraService.getGasolinerasPorProvincia(provinceCode)
                .map(response -> ResponseEntity.ok().body(response))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(500).body(null))); // Manejo de error si es necesario
    }
}
