package es.kuiko.api_comunidades.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.kuiko.api_comunidades.dto.ProvinciaDTO;
import es.kuiko.api_comunidades.dto.ProvinciaInfoComunidadDTO;
import es.kuiko.api_comunidades.exception.CustomNotFoundException;
import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import es.kuiko.api_comunidades.model.Provincia;
import es.kuiko.api_comunidades.service.ProvinciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProvinciaController.class)
class ProvinciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProvinciaService provinciaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Provincia provincia;
    private ProvinciaDTO provinciaDTO;
    private ComunidadAutonoma comunidadAutonoma;

    @BeforeEach
    void setUp() {
        comunidadAutonoma = new ComunidadAutonoma("CA1", "Comunidad Autónoma Test");
        provincia = new Provincia(1, "Provincia Test", comunidadAutonoma);
        provinciaDTO = new ProvinciaDTO(1, "Provincia Test", "CA1");
    }

    @Test
    void testGetAllProvincias() throws Exception {
        when(provinciaService.getAll()).thenReturn(List.of(provincia));

        mockMvc.perform(get("/api-kuiko/provincias/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreProvincia").value("Provincia Test"))
                .andExpect(jsonPath("$[0].codigoProvincia").value(1));

        verify(provinciaService, times(1)).getAll();
    }

    @Test
    void testGetProvinciaById_Success() throws Exception {
        when(provinciaService.getById(1)).thenReturn(Optional.of(provincia));

        mockMvc.perform(get("/api-kuiko/provincias/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreProvincia").value("Provincia Test"))
                .andExpect(jsonPath("$.codigoProvincia").value(1));

        verify(provinciaService, times(1)).getById(1);
    }

    @Test
    void testGetProvinciaById_NotFound() throws Exception {
        when(provinciaService.getById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api-kuiko/provincias/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Provincia no encontrada"));

        verify(provinciaService, times(1)).getById(1);
    }

    @Test
    void testGetProvinciaComunidadInfo_Success() throws Exception {
        ProvinciaInfoComunidadDTO provinciaInfo = new ProvinciaInfoComunidadDTO(1, "Provincia Test", "CA1", "Comunidad Autónoma Test");
        when(provinciaService.getProvinciaComunidadInfoById(1)).thenReturn(Optional.of(provinciaInfo));

        mockMvc.perform(get("/api-kuiko/provincias/1/detalles-comunidad")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreProvincia").value("Provincia Test"))
                .andExpect(jsonPath("$.codigoCa").value("CA1"));

        verify(provinciaService, times(1)).getProvinciaComunidadInfoById(1);
    }

    @Test
    void testGetProvinciaComunidadInfo_NotFound() throws Exception {
        when(provinciaService.getProvinciaComunidadInfoById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api-kuiko/provincias/1/detalles-comunidad")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(provinciaService, times(1)).getProvinciaComunidadInfoById(1);
    }

    @Test
    void testCreateProvincia_Success() throws Exception {
        when(provinciaService.create(any(ProvinciaDTO.class))).thenReturn(provincia);

        mockMvc.perform(post("/api-kuiko/provincias/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(provinciaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreProvincia").value("Provincia Test"))
                .andExpect(jsonPath("$.codigoProvincia").value(1));

        verify(provinciaService, times(1)).create(any(ProvinciaDTO.class));
    }

    @Test
    void testCreateProvincia_Conflict() throws Exception {
        // Configura el mock del servicio para lanzar una excepción en caso de conflicto
        doThrow(new IllegalArgumentException("Ya existe una Provincia con el código 1"))
                .when(provinciaService).create(any(ProvinciaDTO.class));

        // Realiza la solicitud POST al controlador y verifica el estado y el contenido de la respuesta
        mockMvc.perform(post("/api-kuiko/provincias/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(provinciaDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Ya existe una Provincia con el código 1"));

        // Verifica que el método create del servicio se llamó solo una vez
        verify(provinciaService, times(1)).create(any(ProvinciaDTO.class));
    }


    @Test
    void testUpdateProvincia_Success() throws Exception {
        when(provinciaService.update(anyInt(), any(ProvinciaDTO.class))).thenReturn(provincia);

        mockMvc.perform(put("/api-kuiko/provincias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(provinciaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreProvincia").value("Provincia Test"))
                .andExpect(jsonPath("$.codigoProvincia").value(1));

        verify(provinciaService, times(1)).update(anyInt(), any(ProvinciaDTO.class));
    }

    @Test
    void testUpdateProvincia_NotFound() throws Exception {
        // Configuro el mock del servicio para que lance una excepción de CustomNotFoundException
        doThrow(new CustomNotFoundException("Provincia no encontrada"))
                .when(provinciaService).update(anyInt(), any(ProvinciaDTO.class));

        mockMvc.perform(put("/api-kuiko/provincias/1")  // Ajusto el endpoint según sea necesario
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(provinciaDTO)))  // ajusto el contenido según corresponda
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Recurso no encontrado"))  // Verifico el campo "error"
                .andExpect(jsonPath("$.detalles").value("Provincia no encontrada"));  // Verifico el campo "detalles"

        // Verifico que el método del servicio se llamó solo una vez
        verify(provinciaService, times(1)).update(anyInt(), any(ProvinciaDTO.class));
    }


    @Test
    void testDeleteProvincia_Success() throws Exception {
        doNothing().when(provinciaService).delete(anyInt());

        mockMvc.perform(delete("/api-kuiko/provincias/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(provinciaService, times(1)).delete(anyInt());
    }

    @Test
    void testDeleteProvincia_NotFound() throws Exception {
        doThrow(new CustomNotFoundException("Provincia no encontrada"))
                .when(provinciaService).delete(anyInt());

        mockMvc.perform(delete("/api-kuiko/provincias/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Provincia no encontrada"));

        verify(provinciaService, times(1)).delete(anyInt());
    }
}
