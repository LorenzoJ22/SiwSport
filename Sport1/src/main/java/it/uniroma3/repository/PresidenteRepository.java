package it.uniroma3.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.model.Giocatore;
import it.uniroma3.model.Presidente;

import it.uniroma3.model.User;


	public interface PresidenteRepository extends CrudRepository<Presidente,Long> {

		 public Presidente findPresidenteById(Long Id);
		 public void deleteById(Long id);
		 public Boolean existsByNomeAndCognome(String nome, String cognome);
		 
		 @Query(value = "SELECT pp.* FROM presidente pp WHERE pp.squadra_id IS NULL",nativeQuery = true)
		 public Iterable<Presidente> findPresidentiNonAssegnati();
		 
		 @Query(value = "SELECT p.* FROM presidente p JOIN users u ON p.useri_id = u.id WHERE p.useri_id =:UserId",nativeQuery = true)
		 public Presidente findPresidenteByUserId(@Param("UserId")Long id);
	}


