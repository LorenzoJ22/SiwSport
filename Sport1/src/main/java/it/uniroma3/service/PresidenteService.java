package it.uniroma3.service;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;


import it.uniroma3.model.Giocatore;
import it.uniroma3.model.Presidente;
import it.uniroma3.repository.GiocatoreRepository;
import it.uniroma3.repository.PresidenteRepository;
@Service
public class PresidenteService {
	

		@Autowired
		private PresidenteRepository presidenteRepository;


		public Presidente findById(Long Id) {
			return presidenteRepository.findById(Id).get();

		}

		public Iterable<Presidente>findAll(){
			return presidenteRepository.findAll();	
		}

		public Presidente save(Presidente presidente) {
			return presidenteRepository.save(presidente);
		}
		
		public void deleteById(Long id) {
			 this.presidenteRepository.deleteById(id);
		}
		public Boolean existsByNomeAndCognome(String nome, String cognome) {
			return presidenteRepository.existsByNomeAndCognome(nome,  cognome);
		}
		public Iterable<Presidente>findPresidentiNonAssegnati(){
			return presidenteRepository.findPresidentiNonAssegnati();	
		}
		
		public Presidente findPresidenteByUserId(Long id) {
			return presidenteRepository.findPresidenteByUserId(id);
		}
	}


