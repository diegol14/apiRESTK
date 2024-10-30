package es.kuiko.api_comunidades.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.kuiko.api_comunidades.model.Provincia;
import java.util.List;

@Repository
public interface ProvinciaRepository  extends JpaRepository<Provincia, Integer>{

    Optional<Provincia>  findByCodigoProvincia(Integer codigoProvincia);
}