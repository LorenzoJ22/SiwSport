package it.uniroma3.service;
import java.util.List;



import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.model.Credentials;

import it.uniroma3.model.Squadra;

import it.uniroma3.repository.SquadraRepository;

@Service
public class SquadraService {

	@Autowired
	private SquadraRepository squadraRepository;


	public Squadra findById(Long Id) {
		return squadraRepository.findById(Id).get();

	}

	public Iterable<Squadra>findAll(){
		return squadraRepository.findAll();	
	}

	public Iterable<Squadra>findSquadreByIdPresidente(Long id){
		return squadraRepository.FindSquadreByPresidenteId(id);
	}
	public Squadra save(Squadra squadra) {
		return squadraRepository.save(squadra);
	}
	public void deleteById(Long id) {
		squadraRepository.deleteById(id);
	}	
	public Iterable<Squadra> findByName(String nome) {
		return squadraRepository.findByNomeIgnoreCase(nome);
	}
	public Iterable<Squadra> findSquadreSenzaPresidente() {
		return squadraRepository.findSquadreSenzaPresidente();
	}
	
}
