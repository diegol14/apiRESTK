package es.kuiko.api_comunidades.dto;

/**
 * Proyección para obtener información básica sobre una Comunidad Autónoma,
 * incluyendo el código, el nombre y la cantidad de provincias que la componen.
 * 
 * Esta interfaz se utiliza como proyección JPA para optimizar las consultas
 * y solo devolver los campos necesarios de la entidad ComunidadAutonoma.
 */
public interface ComunidadAutonomaCountProvinciasProjection {

    /**
     * Obtiene el código de la Comunidad Autónoma.
     * 
     * @return Código de la Comunidad Autónoma.
     */
    String getCodigoCa();

    /**
     * Obtiene el nombre de la Comunidad Autónoma.
     * 
     * @return Nombre de la Comunidad Autónoma.
     */
    String getNombreCa();

    /**
     * Obtiene la cantidad de provincias en la Comunidad Autónoma.
     * 
     * @return Cantidad de provincias en la Comunidad Autónoma.
     */
    Integer getCantidadProvinciaInComunidad();
}
