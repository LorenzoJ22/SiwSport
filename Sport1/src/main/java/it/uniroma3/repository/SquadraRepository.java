package it.uniroma3.repository;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.model.Credentials;

import it.uniroma3.model.Squadra;

public interface SquadraRepository extends CrudRepository<Squadra,Long> {

	@Query(value = "SELECT * FROM squadra r WHERE r.presidente_id = :idPresidente", nativeQuery = true)
    Iterable<Squadra> FindSquadreByPresidenteId(@Param("idPresidente") Long id);
	
	@Query(value = "SELECT * FROM squadra s WHERE s.presidente_id IS NULL",  nativeQuery = true)
	public Iterable<Squadra> findSquadreSenzaPresidente();
	
	public Iterable<Squadra> findByNome(String nome);
	List<Squadra> findByNomeIgnoreCase(String nome);
}
