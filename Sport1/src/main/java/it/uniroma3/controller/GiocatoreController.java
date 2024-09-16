package it.uniroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.model.Giocatore;
import it.uniroma3.model.Squadra;
import it.uniroma3.service.GiocatoreService;

import it.uniroma3.service.SquadraService;


@Controller // annotazione della classe controller 
public class  GiocatoreController {
	
	@Autowired GiocatoreService giocatoreService;
	@Autowired SquadraService squadraService;
	
	 @GetMapping("/Giocatore/{id}")
		public String getGiocatore(@PathVariable("id")Long id ,Model model) {
			model.addAttribute("Giocatore", this.giocatoreService.findById(id));
			return "Giocatore.html";
		}
	 
	 @GetMapping("/Giocatori")
	 public String getGiocatori(Model model) {
		 model.addAttribute("giocatori", this.giocatoreService.findAll());
		 return "Giocatori.html";
	 }
	 
	 
	 /*giocatori di quella squadra*/
	 @GetMapping("/Squadra/{id}/Giocatori")
	public String getGiocatoriSquadra(@PathVariable("id")Long Id,Model model) {
			model.addAttribute("giocatori", giocatoreService.TrovaGiocatoriBySquadraId(Id));
//			model.addAttribute("Ingredienti", this.ingredienteService.findAll());
			return "Giocatori.html";
		}
	 /*Elenco giocatori, della squadra del presidente, da poter cancellare*/
	 @GetMapping("/GiocatoriPresidente/{id}")
		public String getGiocatoriPresidente(@PathVariable("id")Long Id,Model model) {
				model.addAttribute("Giocatori", giocatoreService.TrovaGiocatoriBySquadraId(Id));
				model.addAttribute("Squadra", squadraService.findById(Id));
				return "pres/GiocatoriPresidente.html";
			}
	 
	 @GetMapping("/pres/GiocatoriPresidente/{id}/{squadraid}")
	    public String cancellaGiocatori(@ModelAttribute("squadra")Squadra squadra,
	    	@PathVariable("id") Long id,@PathVariable("squadraid")Long squadraid, Model model) {  
	    	Giocatore in = giocatoreService.findById(id);
	    	Squadra r = squadraService.findById(squadraid);
	    	
//		 	model.addAttribute("Ricetta", ricettaService.findById(ricettaid));
		 	System.out.println("L'id dell'ingrediente e': "+id);
		 	r.getGiocatori().remove(in);
		 	giocatoreService.deleteById(id);	    	
		 	squadraService.save(r);
// 			ingredienteService.findById(id).getRicette().remove(ricettaService.findById(ricettaid));
	    	/*Capire come tornare ricettaid perchè serve per elimninare dalla tabella la chiave esterna*/
	    	return "redirect:/GiocatoriPresidente/"+squadraid;
//	    	return "redirect:/Squadra/"+squadraid;
	    }



}
