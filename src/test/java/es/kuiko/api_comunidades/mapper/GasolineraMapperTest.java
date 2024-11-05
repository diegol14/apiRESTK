package es.kuiko.api_comunidades.mapper;

import es.kuiko.api_comunidades.dto.gasolineras.GasolineraDTOin;
import es.kuiko.api_comunidades.dto.gasolineras.GasolineraDTOout;
import es.kuiko.api_comunidades.dto.gasolineras.GasolineraWrapperApiResponseIn;
import es.kuiko.api_comunidades.dto.gasolineras.GasolineraWrapperApiResponseOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GasolineraMapperTest {

    private GasolineraMapper gasolineraMapper;

    @BeforeEach
    void setUp() {
        gasolineraMapper = new GasolineraMapper();
    }

    @Test
    void testMapToGasolineraWrapperApiResponseOut() {
        // Crear datos de entrada simulados
        GasolineraDTOin gasolineraDTOin = new GasolineraDTOin();
        gasolineraDTOin.setRotulo("Gasolinera Test");
        gasolineraDTOin.setDireccion("Dirección Test");
        gasolineraDTOin.setHorario("24 horas");
        gasolineraDTOin.setLocalidad("Localidad Test");
        gasolineraDTOin.setProvincia("Provincia Test");

        List<GasolineraDTOin> listaEESSPrecioIn = new ArrayList<>();
        listaEESSPrecioIn.add(gasolineraDTOin);

        GasolineraWrapperApiResponseIn responseIn = new GasolineraWrapperApiResponseIn();
        responseIn.setFecha("2023-11-05");
        responseIn.setNota("Datos correctos");
        responseIn.setResultadoConsulta("OK");
        responseIn.setListaEESSPrecio(listaEESSPrecioIn);

        // Llamar al método de mapeo
        GasolineraWrapperApiResponseOut responseOut = gasolineraMapper.mapToGasolineraWrapperApiResponseOut(responseIn);

        // Verificar la respuesta
        assertNotNull(responseOut, "La respuesta de salida no debería ser nula.");
        assertEquals("2023-11-05", responseOut.getFecha(), "La fecha debería coincidir.");
        assertEquals("Datos correctos", responseOut.getNota(), "La nota debería coincidir.");
        assertEquals("OK", responseOut.getResultadoConsulta(), "El resultado de la consulta debería coincidir.");

        // Verificar la lista de gasolineras en la salida
        assertNotNull(responseOut.getListaEESSPrecio(), "La lista de precios no debería ser nula.");
        assertFalse(responseOut.getListaEESSPrecio().isEmpty(), "La lista de precios no debería estar vacía.");
        GasolineraDTOout gasolineraDTOout = responseOut.getListaEESSPrecio().get(0);
        assertEquals("Gasolinera Test", gasolineraDTOout.getRotulo(), "El rótulo debería coincidir.");
        assertEquals("Dirección Test", gasolineraDTOout.getDireccion(), "La dirección debería coincidir.");
        assertEquals("24 horas", gasolineraDTOout.getHorario(), "El horario debería coincidir.");
        assertEquals("Localidad Test", gasolineraDTOout.getLocalidad(), "La localidad debería coincidir.");
        assertEquals("Provincia Test", gasolineraDTOout.getProvincia(), "La provincia debería coincidir.");
    }
}

