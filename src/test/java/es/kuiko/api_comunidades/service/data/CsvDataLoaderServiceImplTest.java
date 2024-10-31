
package es.kuiko.api_comunidades.service.data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.util.List;

import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import es.kuiko.api_comunidades.model.Provincia;
import es.kuiko.api_comunidades.service.data.CsvDataLoaderServiceImpl;
import org.springframework.core.io.Resource;

import es.kuiko.api_comunidades.repository.ComunidadAutonomaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;


class CsvDataLoaderServiceImplTest {

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private ComunidadAutonomaRepository comunidadRepository;

    @InjectMocks
    private CsvDataLoaderServiceImpl csvDataLoaderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Resource mockResource = mock(Resource.class);
        when(resourceLoader.getResource("ruta_inexistente.csv")).thenReturn(mockResource);
        when(mockResource.exists()).thenReturn(false);
    }


    @Test
    void testLoadComunidades_validData() {
        // Configura el archivo CSV con datos válidos
        // Mock del resourceLoader y comunidadRepository para que el método funcione sin errores reales
        
        // Simula una lista de resultados como respuesta esperada
        List<ComunidadAutonoma> comunidades = csvDataLoaderService.loadComunidades("ruta_valida.csv");
        assertFalse(comunidades.isEmpty(), "La lista de comunidades no debería estar vacía");
    }
    @Test
    void testLoadComunidades_fileNotFound() throws Exception {
        // Crear un recurso simulado para Spring's Resource
        Resource mockResource = mock(Resource.class);
        
        // Configura el mock para devolver false al existir el recurso
        when(resourceLoader.getResource("ruta_inexistente.csv")).thenReturn(mockResource);
        when(mockResource.exists()).thenReturn(false);

        // Ejecuta y verifica que se lanza FileNotFoundException
        assertThrows(FileNotFoundException.class, () -> {
            csvDataLoaderService.loadComunidades("ruta_inexistente.csv");
        });
    }





    @Test
    void testLoadProvincias_validData() {
        // Configura el archivo CSV con datos válidos para provincias
        List<Provincia> provincias = csvDataLoaderService.loadProvincias("ruta_valida.csv");
        assertFalse(provincias.isEmpty(), "La lista de provincias no debería estar vacía");
    }

    @Test
    void testLoadProvincias_invalidDataFormat() {
        // Simula un archivo CSV con datos en formato incorrecto
        assertThrows(IllegalArgumentException.class, () -> {
            csvDataLoaderService.loadProvincias("ruta_con_datos_invalidos.csv");
        });
    }

	@Test
	void testLoadComunidades() {
		fail("Not yet implemented");
	}


	@Test
	void testLoadProvincias() {
		fail("Not yet implemented");
	}

}
