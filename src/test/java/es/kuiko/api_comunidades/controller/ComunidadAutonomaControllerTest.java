package es.kuiko.api_comunidades.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import es.kuiko.api_comunidades.dto.ComunidadAutonomaCountProvinciasDTO;
import es.kuiko.api_comunidades.exception.CustomNotFoundException;
import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import es.kuiko.api_comunidades.service.ComunidadAutonomaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

@WebMvcTest(ComunidadAutonomaController.class)
@ExtendWith(MockitoExtension.class)
class ComunidadAutonomaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComunidadAutonomaService comunidadAutonomaService;

    @Autowired
    private ObjectMapper objectMapper;

    private ComunidadAutonoma comunidadAutonoma;
    private ComunidadAutonomaCountProvinciasDTO countProvinciasDTO;

    @BeforeEach
    void setUp() {
        comunidadAutonoma = new ComunidadAutonoma("CA1", "Comunidad Autónoma 1");
        countProvinciasDTO = new ComunidadAutonomaCountProvinciasDTO("CA1", "Comunidad Autónoma 1", 5);
    }

    @Test
    void testGetAll() throws Exception {
        when(comunidadAutonomaService.getAll()).thenReturn(List.of(comunidadAutonoma));

        mockMvc.perform(get("/api-kuiko/comunidades-autonomas/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigoCa").value("CA1"))
                .andExpect(jsonPath("$[0].nombreCa").value("Comunidad Autónoma 1"));
    }

    @Test
    void testGetByCode_Success() throws Exception {
        when(comunidadAutonomaService.getById("CA1")).thenReturn(Optional.of(comunidadAutonoma));

        mockMvc.perform(get("/api-kuiko/comunidades-autonomas/CA1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigoCa").value("CA1"))
                .andExpect(jsonPath("$.nombreCa").value("Comunidad Autónoma 1"));
    }

    @Test
    void testGetByCode_NotFound() throws Exception {
        when(comunidadAutonomaService.getById("CA2")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api-kuiko/comunidades-autonomas/CA2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Comunidad Autónoma no encontrada"));
    }

    @Test
    void testGetCantidadProvincias_Success() throws Exception {
        when(comunidadAutonomaService.getCantidadProvinciasByComunidad("CA1")).thenReturn(Optional.of(countProvinciasDTO));

        mockMvc.perform(get("/api-kuiko/comunidades-autonomas/CA1/cantidad-provincias")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigoCa").value("CA1"))
                .andExpect(jsonPath("$.nombreCa").value("Comunidad Autónoma 1"))
                .andExpect(jsonPath("$.cantidadProvinciaInComunidad").value(5));
    }

    @Test
    void testGetCantidadProvincias_NotFound() throws Exception {
        when(comunidadAutonomaService.getCantidadProvinciasByComunidad("CA2")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api-kuiko/comunidades-autonomas/CA2/cantidad-provincias")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreate() throws Exception {
        when(comunidadAutonomaService.create(any(ComunidadAutonoma.class))).thenReturn(comunidadAutonoma);

        mockMvc.perform(post("/api-kuiko/comunidades-autonomas/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comunidadAutonoma)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codigoCa").value("CA1"))
                .andExpect(jsonPath("$.nombreCa").value("Comunidad Autónoma 1"));
    }

    @Test
    void testUpdate_Success() throws Exception {
        when(comunidadAutonomaService.update(anyString(), any(ComunidadAutonoma.class))).thenReturn(comunidadAutonoma);

        mockMvc.perform(put("/api-kuiko/comunidades-autonomas/CA1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comunidadAutonoma)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigoCa").value("CA1"))
                .andExpect(jsonPath("$.nombreCa").value("Comunidad Autónoma 1"));
    }

    @Test
    void testUpdate_NotFound() throws Exception {
        // Configura el mock para lanzar CustomNotFoundException
        when(comunidadAutonomaService.update(anyString(), any(ComunidadAutonoma.class)))
                .thenThrow(new CustomNotFoundException("Comunidad Autónoma no encontrada"));

        // Realiza la solicitud de actualización
        mockMvc.perform(put("/api-kuiko/comunidades-autonomas/CA2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comunidadAutonoma)))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"error\":\"Recurso no encontrado\",\"detalles\":\"Comunidad Autónoma no encontrada\"}"));
    }


    @Test
    void testDelete_Success() throws Exception {
        mockMvc.perform(delete("/api-kuiko/comunidades-autonomas/CA1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDelete_NotFound() throws Exception {
        doThrow(new RuntimeException("Comunidad Autónoma no encontrada")).when(comunidadAutonomaService).delete("CA2");

        mockMvc.perform(delete("/api-kuiko/comunidades-autonomas/CA2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Comunidad Autónoma no encontrada"));
    }
}
