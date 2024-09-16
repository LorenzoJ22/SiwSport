package it.uniroma3.controller;

import java.util.ArrayList;







import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.model.Credentials;

import it.uniroma3.model.Giocatore;
import it.uniroma3.model.Presidente;
import it.uniroma3.model.Squadra;
import it.uniroma3.model.User;
import it.uniroma3.service.CredentialsService;
import it.uniroma3.service.GiocatoreService;
import it.uniroma3.service.PresidenteService;
import it.uniroma3.service.SquadraService;
import it.uniroma3.service.UserService;


@Controller // annotazione della classe controller 
public class  SquadraController {
	
	
	@Autowired CredentialsService credentialsService;
	@Autowired PresidenteService presidenteService;
	@Autowired SquadraService squadraService;
	@Autowired GiocatoreService giocatoreService;
	
	@GetMapping("/Squadre")
	public String GetAllRicette(Model model) {
	model.addAttribute("squadre",this.squadraService.findAll());
		return "Squadre.html";
	}
	
	
    @GetMapping("/Squadra/{id}")
	public String getRicetta(@PathVariable("id")Long id ,Model model) {
		model.addAttribute("Squadra", this.squadraService.findById(id));
		return "Squadra.html";
//		return "redirect:/Ricetta/"+id;
	}
    
    @GetMapping("/SquadreAdmin")
    public String GetAllSquadreAdmin(Model model) {
    	model.addAttribute("Squadre",this.squadraService.findSquadreSenzaPresidente());
    		return "admin/SquadreAdmin.html";
    	}
    
    @GetMapping("/CancellaSquadra")
    public String GetAllSquadreAdminCanc(Model model) {
    	model.addAttribute("Squadre",this.squadraService.findAll());
    		return "admin/CancellaSquadra.html";
    	}
    
    
    @GetMapping("/SquadraP/{id}")
    public String getSquadreId(@PathVariable("id")Long id, Model model) {
				model.addAttribute("SquadraPresidente", squadraService.findSquadreByIdPresidente(id));
				return "pres/SquadraPresidente.html";
		}
    
    

    
/*Form per aggiungere giocatori alla squadra del presidente corrente*/
    @GetMapping("/NewGiocatori/{id}")
    public String addGiocatori(@ModelAttribute("squadra")Squadra squadra,
    		@PathVariable("id") Long id, Model model) {    	
    	    model.addAttribute("giocatore", new Giocatore());
    	    model.addAttribute("squadra", squadraService.findById(id));
//    	    List<Ingrediente> ingredienti= ricettaService.findIngredientiByIdRicetta(id);
    	           
    	return "pres/formNewGiocatori.html";
    }
    
    @PostMapping("/GiocatoriNuovi")
	public String newGiocatori( @ModelAttribute("giocatore")Giocatore giocatore,
			@RequestParam("id")Long id,
			 Model model) {
    	Squadra squadras = squadraService.findById(id);
    	
    	 if (giocatore.getDataInizio() == null || giocatore.getDataFine() == null) {
    	        model.addAttribute("messaggioErrore", "Le date di inizio e fine non possono essere vuote.");
    	        return "pres/formNewGiocatori.html"; // Torna alla pagina del form
    	    }else {
    	    
    	    
    	giocatore.setId(null);
    	
    	giocatore.setSquadra(squadras);
//    	squadras.getGiocatori().add(giocatore);
    	this.giocatoreService.save(giocatore);
//    	squadraService.save(squadras);
    	
	    System.out.println("I giocatori sono:"+squadras.getGiocatori());
	    // Salva il giocatore e aggiorna la squadra
	    System.out.println("L'id e': "+squadras.getId());
	    return "redirect:/Squadra/" + squadras.getId()+"/Giocatori"; }
//		return "redirect:Giocatori";
	}
    
    
    /*SearchRicetta form da IndexCook*/
    @GetMapping("/formSearchSquadra")
	public String formSearchSquadra() {
		return "formSearchSquadra.html";
	}
    /*fare in modo che quando scrivo qualcosa lo prenda sia da maiuscolo che da minuscolo*/
	@PostMapping("/searchSquadra")
	public String searchSquadra(Model model, @RequestParam("nome")String nome) {
//		model.addAttribute("ricette", this.ricettaService.findByName(name));
		model.addAttribute("squadre", squadraService.findByName(nome));
		return "foundSquadra.html";
//		return "redirect:/foundRicetta";
	}
    	
    }
    



