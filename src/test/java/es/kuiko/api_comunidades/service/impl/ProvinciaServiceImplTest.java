package es.kuiko.api_comunidades.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.kuiko.api_comunidades.dto.ProvinciaDTO;
import es.kuiko.api_comunidades.dto.ProvinciaInfoComunidadDTO;
import es.kuiko.api_comunidades.exception.CustomNotFoundException;
import es.kuiko.api_comunidades.exception.IllegalUpdateException;
import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import es.kuiko.api_comunidades.model.Provincia;
import es.kuiko.api_comunidades.repository.ComunidadAutonomaRepository;
import es.kuiko.api_comunidades.repository.ProvinciaRepository;

@ExtendWith(MockitoExtension.class)
class ProvinciaServiceImplTest {

    @Mock
    private ProvinciaRepository provinciaRepository;

    @Mock
    private ComunidadAutonomaRepository comunidadAutonomaRepository;

    @InjectMocks
    private ProvinciaServiceImpl provinciaServiceImpl;
    
    private ComunidadAutonoma ca1;

    @BeforeEach
    void setUp() {
        // Inicialización de ComunidadAutonoma simulada
        ca1 = new ComunidadAutonoma("ca1", "Comunidad Autónoma 1");
    }

    @Test
    void testGetAll() {
        // Configurar datos simulados

        Provincia provincia1 = new Provincia(1, "Provincia 1", ca1);
        Provincia provincia2 = new Provincia(2, "Provincia 2", ca1);
        List<Provincia> provinciasSimuladas = Arrays.asList(provincia1, provincia2);

        when(provinciaRepository.findAll()).thenReturn(provinciasSimuladas);

        // Ejecutar el método bajo prueba
        List<Provincia> resultado = provinciaServiceImpl.getAll();

        // Verificar el resultado
        assertEquals(2, resultado.size());
        assertEquals("Provincia 1", resultado.get(0).getNombreProvincia());
        verify(provinciaRepository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        // Preparar la Provincia simulada
        Provincia provinciaSimulada = new Provincia(1, "Provincia 1", ca1);

        // Mock para que `findById` retorne la Provincia simulada
        when(provinciaRepository.findById(1)).thenReturn(Optional.of(provinciaSimulada));

        // Llamar al método bajo prueba
        Optional<Provincia> resultado = provinciaServiceImpl.getById(1);

        // Verificar el resultado
        assertTrue(resultado.isPresent(), "La provincia no fue encontrada cuando debería existir.");
        assertEquals("Provincia 1", resultado.get().getNombreProvincia(), "El nombre de la provincia no coincide.");
        
        // Verificar que se llamó al método `findById` solo una vez
        verify(provinciaRepository, times(1)).findById(1);
    }


    @Test
    void testGetProvinciaComunidadInfoById() {
        Provincia provinciaSimulada = new Provincia(1, "Provincia 1", ca1);

        when(provinciaRepository.existsById(1)).thenReturn(true);// le dice que existe la provincia simulada, ya que yo tengo validación sobre la BBDD
        when(provinciaRepository.findProvinciaComunidadInfoById(1)).thenReturn(Optional.of(provinciaSimulada));

        Optional<ProvinciaInfoComunidadDTO> resultado = provinciaServiceImpl.getProvinciaComunidadInfoById(1);

        assertTrue(resultado.isPresent());
        assertEquals("Provincia 1", resultado.get().getNombreProvincia());
        assertEquals("ca1", resultado.get().getCodigoCa());
        verify(provinciaRepository, times(1)).findProvinciaComunidadInfoById(1);
    }

    @Test
    void testCreate() {
        ProvinciaDTO provinciaDTO = new ProvinciaDTO(1, "Provincia 1", ca1.getCodigoCa());
        
        when(comunidadAutonomaRepository.findById("ca1")).thenReturn(Optional.of(ca1));
        when(provinciaRepository.existsById(1)).thenReturn(false);
        when(provinciaRepository.save(any(Provincia.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Provincia resultado = provinciaServiceImpl.create(provinciaDTO);

        assertNotNull(resultado);
        assertEquals("Provincia 1", resultado.getNombreProvincia());
        assertEquals("ca1", resultado.getComunidadAutonoma().getCodigoCa());
        verify(provinciaRepository, times(1)).save(any(Provincia.class));
    }

    @Test
    void testUpdate() {
        ProvinciaDTO provinciaModificadaDTO = new ProvinciaDTO(1, "Provincia Modificada", ca1.getCodigoCa());
        Provincia provinciaExistente = new Provincia(1, "Provincia Original", ca1);

        when(provinciaRepository.findById(1)).thenReturn(Optional.of(provinciaExistente));
        when(provinciaRepository.save(any(Provincia.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Provincia resultado = provinciaServiceImpl.update(1, provinciaModificadaDTO);

        assertNotNull(resultado);
        assertEquals("Provincia Modificada", resultado.getNombreProvincia());
        verify(provinciaRepository, times(1)).save(any(Provincia.class));
    }

    @Test
    void testDelete() {
        when(provinciaRepository.existsById(1)).thenReturn(true);

        provinciaServiceImpl.delete(1);

        verify(provinciaRepository, times(1)).deleteById(1);
    }

    @Test
    void testCreateProvinciaAlreadyExists() {
        ProvinciaDTO provinciaDTO = new ProvinciaDTO(1, "Provincia 1", ca1.getCodigoCa());

        when(provinciaRepository.existsById(1)).thenReturn(true);
        when(comunidadAutonomaRepository.findById("ca1")).thenReturn(Optional.of(ca1));  // Devuelve un Optional con `ca1`

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            provinciaServiceImpl.create(provinciaDTO);
        });

        assertEquals("Ya existe una Provincia con el código 1", exception.getMessage());
    }


    @Test
    void testUpdateProvinciaNotFound() {
        ProvinciaDTO provinciaModificadaDTO = new ProvinciaDTO(1, "Provincia Modificada", ca1.getCodigoCa());

        when(provinciaRepository.findById(1)).thenReturn(Optional.empty());

        CustomNotFoundException exception = assertThrows(CustomNotFoundException.class, () -> {
            provinciaServiceImpl.update(1, provinciaModificadaDTO);
        });

        assertEquals("Provincia con código 1 no encontrada", exception.getMessage());
    }
}

