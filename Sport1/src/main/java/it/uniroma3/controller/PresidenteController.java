package it.uniroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.service.GiocatoreService;
import it.uniroma3.service.PresidenteService;
import it.uniroma3.service.SquadraService;

@Controller
public class PresidenteController {
	@Autowired PresidenteService presidenteService;
	@Autowired SquadraService squadraService;
	
	 @GetMapping("/Presidente/{id}")
		public String getPresidente(@PathVariable("id")Long id ,Model model) {
			model.addAttribute("Presidente", this.presidenteService.findById(id));
			return "Presidente.html";
		}
	 
	 @GetMapping("/Presidenti")
	 public String getPresidenti(Model model) {
		 model.addAttribute("presidenti", this.presidenteService.findAll());
		 return "Presidenti.html";
	 }
}
