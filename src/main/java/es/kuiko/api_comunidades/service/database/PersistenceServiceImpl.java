package es.kuiko.api_comunidades.service.database;

import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import es.kuiko.api_comunidades.model.Provincia;
import es.kuiko.api_comunidades.repository.ComunidadAutonomaRepository;
import es.kuiko.api_comunidades.repository.ProvinciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de {@link PersistenceService} para la persistencia de entidades de Comunidades Autónomas y Provincias.
 * 
 * <p>
 * La clase {@code PersistenceServiceImpl} proporciona métodos para guardar entidades
 * {@link ComunidadAutonoma} y {@link Provincia} en la base de datos, verificando previamente
 * si cada entidad ya existe para evitar duplicados.
 * </p>
 * 
 * <p>
 * Si una entidad con el mismo código ya existe en la base de datos, el registro se omite
 * y se notifica a través de un mensaje de consola.
 * </p>
 */
@Service
public class PersistenceServiceImpl implements PersistenceService {

    private final ComunidadAutonomaRepository comunidadAutonomaRepository;
    private final ProvinciaRepository provinciaRepository;

    /**
     * Constructor que inyecta las dependencias de los repositorios de Comunidades Autónomas y Provincias.
     *
     * @param comunidadAutonomaRepository Repositorio para la persistencia de Comunidades Autónomas.
     * @param provinciaRepository Repositorio para la persistencia de Provincias.
     */
    public PersistenceServiceImpl(ComunidadAutonomaRepository comunidadAutonomaRepository, ProvinciaRepository provinciaRepository) {
        this.comunidadAutonomaRepository = comunidadAutonomaRepository;
        this.provinciaRepository = provinciaRepository;
    }

    /**
     * Guarda una lista de entidades {@link ComunidadAutonoma} en la base de datos.
     *
     * <p>
     * Para cada comunidad en la lista, el método verifica si existe en la base de datos
     * una comunidad con el mismo código. Si no existe, se guarda la comunidad; en caso contrario,
     * se omite el guardado y se muestra un mensaje en la consola.
     * </p>
     *
     * @param comunidades Lista de entidades {@link ComunidadAutonoma} a guardar.
     */
    @Override
    public void saveComunidades(List<ComunidadAutonoma> comunidades) {
        comunidades.forEach(comunidad -> {
            if (!comunidadAutonomaRepository.existsByCodigoCa(comunidad.getCodigoCa())) {
                comunidadAutonomaRepository.save(comunidad);
                System.out.println("Comunidad Autónoma guardada: " + comunidad);
            } else {
                System.out.println("La comunidad con código " + comunidad.getCodigoCa() + " ya existe, se omite.");
            }
        });
    }

    /**
     * Guarda una lista de entidades {@link Provincia} en la base de datos.
     *
     * <p>
     * Para cada provincia en la lista, el método verifica si existe en la base de datos
     * una provincia con el mismo código. Si no existe, se guarda la provincia; en caso contrario,
     * se omite el guardado y se muestra un mensaje en la consola.
     * </p>
     *
     * @param provincias Lista de entidades {@link Provincia} a guardar.
     */
    @Override
    public void saveProvincias(List<Provincia> provincias) {
        provincias.forEach(provincia -> {
            // Verificar si existe una provincia con el mismo código
            if (!provinciaRepository.findByCodigoProvincia(provincia.getCodigoProvincia()).isPresent()) {
                provinciaRepository.save(provincia);
                System.out.println("Provincia guardada: " + provincia);
            } else {
                System.out.println("La provincia con código " + provincia.getCodigoProvincia() + " ya existe, se omite.");
            }
        });
    }

}
