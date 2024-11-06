package es.kuiko.api_comunidades.service.impl;

import es.kuiko.api_comunidades.dto.ComunidadAutonomaCountProvinciasDTO;
import es.kuiko.api_comunidades.exception.CustomNotFoundException;
import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import es.kuiko.api_comunidades.repository.ComunidadAutonomaRepository;
import es.kuiko.api_comunidades.repository.ProvinciaRepository;
import es.kuiko.api_comunidades.service.ComunidadAutonomaService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio {@link ComunidadAutonomaService} para gestionar
 * operaciones CRUD sobre las entidades de Comunidades Autónomas.
 * 
 * <p>Esta clase proporciona métodos para crear, actualizar, eliminar y recuperar
 * Comunidades Autónomas, así como un método adicional para contar el número de provincias
 * asociadas a cada comunidad.
 * 
 * <p>Incluye validaciones internas para asegurar que los códigos de comunidad
 * sean válidos y que las entidades existan antes de realizar las operaciones pertinentes.
 */
@Service
@Validated
public class ComunidadAutonomaServiceImpl implements ComunidadAutonomaService {

    private final ComunidadAutonomaRepository comunidadAutonomaRepository;

    /**
     * Constructor para inyectar dependencias de repositorios.
     *
     * @param comunidadAutonomaRepository Repositorio de Comunidad Autónoma para realizar operaciones en la base de datos.
     */
    public ComunidadAutonomaServiceImpl(ComunidadAutonomaRepository comunidadAutonomaRepository) {
        this.comunidadAutonomaRepository = comunidadAutonomaRepository;
    }

    /**
     * Recupera todas las Comunidades Autónomas.
     *
     * @return Una lista de todas las Comunidades Autónomas.
     */
    @Override
    public List<ComunidadAutonoma> getAll() {
        return comunidadAutonomaRepository.findAll();
    }

    /**
     * Recupera una Comunidad Autónoma por su código único.
     *
     * @param codigoCa Código de la Comunidad Autónoma.
     * @return Un {@link Optional} que contiene la Comunidad Autónoma si se encuentra.
     */
    @Override
    public Optional<ComunidadAutonoma> getById(String codigoCa) {
        validateCodigoCa(codigoCa);  
        return comunidadAutonomaRepository.findById(codigoCa);
    }
    
    /**
     * Cuenta el número de provincias en una Comunidad Autónoma específica.
     *
     * @param codigoCa Código de la Comunidad Autónoma.
     * @return Un {@link Optional} que contiene el DTO con la cantidad de provincias y los datos de la comunidad.
     * @throws CustomNotFoundException si la Comunidad Autónoma no existe.
     */
    public Optional<ComunidadAutonomaCountProvinciasDTO> getCantidadProvinciasByComunidad(String codigoCa) {
        validateCodigoCa(codigoCa);
        ensureComunidadExists(codigoCa);

        return comunidadAutonomaRepository.findCantidadProvinciasPorComunidad(codigoCa)
            .map(projection -> new ComunidadAutonomaCountProvinciasDTO(
                projection.getCodigoCa(),
                projection.getNombreCa(),
                projection.getCantidadProvinciaInComunidad()
            ));
    }

    /**
     * Crea una nueva Comunidad Autónoma.
     *
     * @param comunidadAutonoma La entidad ComunidadAutonoma a crear.
     * @return La entidad ComunidadAutonoma creada.
     * @throws IllegalArgumentException si ya existe una Comunidad Autónoma con el mismo código.
     */
    @Override
    public ComunidadAutonoma create(ComunidadAutonoma comunidadAutonoma) {
        validateCodigoCa(comunidadAutonoma);
        if (doesComunidadExist(comunidadAutonoma.getCodigoCa())) {
            throw new IllegalArgumentException("Ya existe una Comunidad Autónoma con el código " + comunidadAutonoma.getCodigoCa());
        }
        return comunidadAutonomaRepository.save(comunidadAutonoma);
    }

    /**
     * Actualiza una Comunidad Autónoma existente.
     *
     * @param codigoCa Código de la Comunidad Autónoma a actualizar.
     * @param comunidadAutonoma La entidad ComunidadAutonoma con los nuevos datos.
     * @return La entidad actualizada.
     * @throws CustomNotFoundException si la Comunidad Autónoma no existe.
     */
    @Override
    public ComunidadAutonoma update(String codigoCa, ComunidadAutonoma comunidadAutonoma) {
        validateCodigoCa(codigoCa);
        ensureComunidadExists(codigoCa);
        comunidadAutonoma.setCodigoCa(codigoCa);
        return comunidadAutonomaRepository.save(comunidadAutonoma);
    }

    /**
     * Elimina una Comunidad Autónoma por su código.
     *
     * @param codigoCa Código de la Comunidad Autónoma a eliminar.
     * @throws CustomNotFoundException si la Comunidad Autónoma no existe.
     */
    @Override
    public void delete(String codigoCa) {
        validateCodigoCa(codigoCa);
        ensureComunidadExists(codigoCa);
        comunidadAutonomaRepository.deleteById(codigoCa);
    }

    // Métodos internos de validación para evitar duplicación (DRY)

    /**
     * Valida el código de una Comunidad Autónoma en la entidad proporcionada.
     *
     * @param comunidadAutonoma La entidad ComunidadAutonoma a validar.
     * @throws IllegalArgumentException si el código es inválido.
     */
    private void validateCodigoCa(ComunidadAutonoma comunidadAutonoma) {
        validateCodigoCa(comunidadAutonoma.getCodigoCa());
    }

    /**
     * Valida el código de una Comunidad Autónoma.
     *
     * @param codigoCa El código de la Comunidad Autónoma a validar.
     * @throws IllegalArgumentException si el código es nulo, vacío o contiene caracteres no válidos.
     */
    private void validateCodigoCa(String codigoCa) {
        if (codigoCa == null || codigoCa.isBlank()) {
            throw new IllegalArgumentException("El código de la Comunidad no puede ser nulo ni estar vacío");
        }
        if (!codigoCa.matches("[A-Za-z0-9]+")) {
            throw new IllegalArgumentException("El código de la Comunidad contiene caracteres no válidos");
        }
    }
    
    /**
     * Verifica que una Comunidad Autónoma exista por su código. Lanza una excepción si no existe.
     *
     * @param codigoCa El código de la Comunidad Autónoma a verificar.
     * @throws CustomNotFoundException si la Comunidad Autónoma no existe.
     */
    private void ensureComunidadExists(String codigoCa) {
        if (!doesComunidadExist(codigoCa)) {
            throw new CustomNotFoundException("Comunidad Autónoma no encontrada");
        }
    }

    /**
     * Verifica la existencia de una Comunidad Autónoma en la base de datos.
     *
     * @param codigoCa El código de la Comunidad Autónoma.
     * @return true si existe, false en caso contrario.
     */
    private boolean doesComunidadExist(String codigoCa) {
        return comunidadAutonomaRepository.existsById(codigoCa);
    }
}
