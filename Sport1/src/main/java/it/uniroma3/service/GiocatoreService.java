package it.uniroma3.service;
import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Service;


import it.uniroma3.model.Giocatore;
import it.uniroma3.repository.GiocatoreRepository;


@Service

public class GiocatoreService {

	@Autowired
	private GiocatoreRepository giocatoreRepository;


	public Giocatore findById(Long Id) {
		return giocatoreRepository.findById(Id).get();

	}

	public Iterable<Giocatore>findAll(){
		return giocatoreRepository.findAll();	
	}

	public Giocatore save(Giocatore giocatore) {
		return giocatoreRepository.save(giocatore);
	}
	
	public void deleteById(Long id) {
		 this.giocatoreRepository.deleteById(id);
	}

	public Iterable<Giocatore>TrovaGiocatoriBySquadraId(Long id){
		return giocatoreRepository.TrovaGiocatoriBySquadraId(id);
	}
}
