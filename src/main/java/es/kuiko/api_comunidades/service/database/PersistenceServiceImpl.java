package es.kuiko.api_comunidades.service.database;

import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import es.kuiko.api_comunidades.model.Provincia;
import es.kuiko.api_comunidades.repository.ComunidadAutonomaRepository;
import es.kuiko.api_comunidades.repository.ProvinciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersistenceServiceImpl implements PersistenceService {

    private final ComunidadAutonomaRepository comunidadAutonomaRepository;
    private final ProvinciaRepository provinciaRepository;

    public PersistenceServiceImpl(ComunidadAutonomaRepository comunidadAutonomaRepository, ProvinciaRepository provinciaRepository) {
        this.comunidadAutonomaRepository = comunidadAutonomaRepository;
        this.provinciaRepository = provinciaRepository;
    }

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
