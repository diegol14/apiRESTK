package es.kuiko.api_comunidades.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.kuiko.api_comunidades.model.ComunidadAutonoma;
import java.util.List;

@Repository
public interface ComunidadAutonomaRepository extends JpaRepository<ComunidadAutonoma, String> {

	Optional<ComunidadAutonoma> findByCodigoCa(String codigoCa);

	boolean existsByCodigoCa(String codigoCa);

}