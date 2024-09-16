package it.uniroma3.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import it.uniroma3.model.Giocatore;

import it.uniroma3.model.User;

public interface GiocatoreRepository extends CrudRepository<Giocatore,Long> {

	 public Giocatore findGiocatoreById(Long Id);
	 public void deleteById(Long id);
	 
	 @Query(value = "SELECT g.* FROM squadra s JOIN giocatore g ON s.id = g.squadra_id WHERE g.squadra_id =:fid", nativeQuery = true)
	 Iterable<Giocatore> TrovaGiocatoriBySquadraId(@Param("fid")Long id);
		
}
