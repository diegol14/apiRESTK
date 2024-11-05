package es.kuiko.api_comunidades.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import es.kuiko.api_comunidades.repository.ComunidadAutonomaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ComunidadAutonomaServiceImplTest {

    @Mock
    private ComunidadAutonomaRepository comunidadAutonomaRepository;

    @InjectMocks
    private ComunidadAutonomaServiceImpl comunidadAutonomaService;

    @BeforeEach
    void setUp() throws Exception {
        // Este método se ejecuta antes de cada prueba. Si necesitas configuraciones adicionales, agrégalas aquí.
    	// En este caso, Mockito inicializará automáticamente los mocks gracias a las anotaciones @Mock y @InjectMocks.
    }
    
    @Test
    void testGetAll() {
        // Paso 1: Preparar datos simulados
        ComunidadAutonoma ca1 = new ComunidadAutonoma("CA1", "Comunidad Autónoma 1");
        ComunidadAutonoma ca2 = new ComunidadAutonoma("CA2", "Comunidad Autónoma 2");
        
        List<ComunidadAutonoma> comunidades = Arrays.asList(ca1, ca2);

        /* Configurar que hace el mock */
        when(comunidadAutonomaRepository.findAll()).thenReturn(comunidades);

        // Llamar al método bajo prueba
        List<ComunidadAutonoma> result = comunidadAutonomaService.getAll();

        // Paso 4: Verificar el resultado
        assertEquals(2, result.size(), "La cantidad de comunidades no coincide");
        verify(comunidadAutonomaRepository, times(1)).findAll();
    }
    
    @Test
    void testGetByIdWhenExists() {
        ComunidadAutonoma comunidad = new ComunidadAutonoma("CA1", "Comunidad Autónoma 1");
        when(comunidadAutonomaRepository.findById("CA1")).thenReturn(Optional.of(comunidad));

        Optional<ComunidadAutonoma> result = comunidadAutonomaService.getById("CA1");

        assertTrue(result.isPresent(), "La comunidad debería estar presente");
        assertEquals("CA1", result.get().getCodigoCa(), "El código de la comunidad no coincide");
    }

    @Test
    void testGetByIdWhenNotExists() {
        when(comunidadAutonomaRepository.findById("CA1")).thenReturn(Optional.empty());

        Optional<ComunidadAutonoma> result = comunidadAutonomaService.getById("CA1");

        assertFalse(result.isPresent(), "La comunidad no debería estar presente");
    }
    
    @Test
    void testCreateWhenAlreadyExists() {
        ComunidadAutonoma comunidad = new ComunidadAutonoma("CA1", "Comunidad Autónoma 1");
        when(comunidadAutonomaRepository.existsById("CA1")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            comunidadAutonomaService.create(comunidad);
        });
        assertEquals("Ya existe una ComunidadAutonoma con el código CA1", exception.getMessage());
    }
    
    @Test
    void testCreateWhenDoesNotExist() {
        ComunidadAutonoma comunidad = new ComunidadAutonoma("CA1", "Comunidad Autónoma 1");
        when(comunidadAutonomaRepository.existsById("CA1")).thenReturn(false);
        when(comunidadAutonomaRepository.save(comunidad)).thenReturn(comunidad);

        ComunidadAutonoma result = comunidadAutonomaService.create(comunidad);

        assertNotNull(result, "La comunidad no debe ser nula");
        assertEquals("CA1", result.getCodigoCa(), "El código de la comunidad no coincide");
        verify(comunidadAutonomaRepository, times(1)).save(comunidad);
    }
    
    @Test
    void testUpdateWhenExists() {
        ComunidadAutonoma comunidad = new ComunidadAutonoma("CA1", "Comunidad Autónoma Actualizada");
        when(comunidadAutonomaRepository.existsById("CA1")).thenReturn(true);
        when(comunidadAutonomaRepository.save(comunidad)).thenReturn(comunidad);

        ComunidadAutonoma result = comunidadAutonomaService.update("CA1", comunidad);

        assertNotNull(result, "La comunidad no debe ser nula");
        assertEquals("Comunidad Autónoma Actualizada", result.getNombreCa(), "El nombre de la comunidad no coincide");
        verify(comunidadAutonomaRepository, times(1)).save(comunidad);
    }
    
    @Test
    void testUpdateWhenNotExists() {
        ComunidadAutonoma comunidad = new ComunidadAutonoma("CA1", "Comunidad Autónoma 1");
        when(comunidadAutonomaRepository.existsById("CA1")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            comunidadAutonomaService.update("CA1", comunidad);
        });
        assertEquals("Comunidad Autónoma no encontrada", exception.getMessage());
    }
    @Test
    void testDeleteWhenExists() {
        when(comunidadAutonomaRepository.existsById("CA1")).thenReturn(true);

        comunidadAutonomaService.delete("CA1");

        verify(comunidadAutonomaRepository, times(1)).deleteById("CA1");
    }
    
    @Test
    void testDeleteWhenNotExists() {
        when(comunidadAutonomaRepository.existsById("CA1")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            comunidadAutonomaService.delete("CA1");
        });
        assertEquals("Comunidad Autónoma no encontrada", exception.getMessage());
    }


}
