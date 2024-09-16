package it.uniroma3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;






import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import it.uniroma3.validator.CredentialsValidator;
import it.uniroma3.validator.UserValidator;


@Controller
public class AdminController {
	 @Autowired
	 private UserService userService;
		@Autowired
		GiocatoreService giocatoreService;
	@Autowired
	PresidenteService presidenteService;
	
	@Autowired
	CredentialsService credentialsService;
	@Autowired SquadraService squadraService;
	@Autowired 
	private CredentialsValidator credentialsValidator;
	@Autowired
	private UserValidator userValidator;
	
	@GetMapping(value = "/registerAdmin") 
	public String showRegisterFormAdmin (Model model) {
//		model.addAttribute("presidente", new Presidente());
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
//		model.addAttribute("role",Credentials.ADMIN_ROLE);
		return "registerAdmin.html";
	}
	
	
	@PostMapping(value = { "/registerAdmin" })
    public String registerAdmin( @ModelAttribute("presidente") Presidente presidente,
    						    @ModelAttribute("user") User user, 
                 BindingResult userBindingResult, 
                 @ModelAttribute("credentials") Credentials credentials,
                 BindingResult credentialsBindingResult,
                 Model model) {
    	
		this.credentialsValidator.validate(credentials, credentialsBindingResult);
		this.userValidator.validate(user, userBindingResult);
		
		// se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        if(!credentialsBindingResult.hasErrors()) {
//        	userService.saveUser(user);
        	credentialsService.saveCredentialsAdmin(credentials);
            credentials.setUser(user);
            model.addAttribute("user", user);
//            credentials.setPresidente(presidente);
//            presidente.setCognome(user.getSurname());
//            presidente.setNome(user.getName());
//            this.presidenteService.save(presidente);
           /*non serve*/
            userService.saveUser(user);
            System.out.println("L'utente registrato e': "+credentials.getRole());
            
            return "login.html";
        }
        return "registerAdmin.html";
    }
	
	@GetMapping("/NewSquadraAdmin")
	public String addSquadraAdmin(Model model) {
	    model.addAttribute("squadra", new Squadra());
	    model.addAttribute("presidenti", presidenteService.findPresidentiNonAssegnati());
	    return "admin/formNewSquadraAdmin.html"; // Nome corretto della vista
	}

	@PostMapping("/NewsquadreAdmin")
	public String newSquadraAdmin(
	        @ModelAttribute("squadra") Squadra squadra,
	        @RequestParam(value="presidente", required = false) Long presidenteId,
	        Model model) {


		if (presidenteId != null) {
	        Presidente presidente = presidenteService.findById(presidenteId);
	        squadra.setPresidente(presidente); // Associa il presidente selezionato alla squadra
	        presidente.setSquadra(squadra);
	    } else {
	        squadra.setPresidente(null); // Nessun presidente selezionato
	    }

	    squadraService.save(squadra); // Salva la squadra con o senza presidente

	    model.addAttribute("squadre", squadraService.findAll());
	    return "redirect:/Squadre"; // Redirigi alla pagina che mostra tutte le squadre
	}

	
	
	
//	/*Aggiungi squadra da Admin senza i giocatori*/
//	@GetMapping("/NewSquadraAdmin")
//    public String addSquadraAdmin(@ModelAttribute("presidente") Presidente presidente, 
//    		Model model) {    	
//    	model.addAttribute("squadra", new Squadra());
//    	model.addAttribute("presidenti", presidenteService.findAll());
//    	return "admin/formNewRicettaAdmin.html";
//    }
//	
//	@PostMapping("/squadreAdmin")
//	public String newSquadraAdmin(
//			@ModelAttribute("squadra")Squadra squadra,@RequestParam("presidente") Long presidenteId,@ModelAttribute("presidente")Presidente presidente, Model model) {
//		
////		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
////		User user= credentials.getUser();
//		 Presidente presidentes = presidenteService.findById(presidenteId);
//		    squadra.setPresidente(presidentes);
//		    
//		    squadraService.save(squadra);
//		    
//	    squadra.setPresidente(presidente);
//	    squadra.getPresidente().setId(presidente.getId());
////		presidente.getSquadre().add(ricetta);
//		squadraService.save(squadra);
//		presidenteService.save(presidente);
//		model.addAttribute("Squadra", this.squadraService.findAll());
//		return "Ricette.html";
//		//return "redirect:Ricetta/"+ricetta.getId();
//	}
	
	/*Cancella squadra senza presidente dall'elenco dell'admin*/
	 @GetMapping("admin/CancellaSquadreAdmin/{id}")
	    public String cancellaSquadra(@ModelAttribute("squadra")Squadra squadra,
	    	@PathVariable("id") Long id, Model model) {  
//	    	Ricetta ricettas = ricettaService.findById(id);
//	    	Ingrediente in = ingredienteService.findById(idIn);
	    	Squadra r = squadraService.findById(id);
	    	model.addAttribute("Squadra",r);
		 	squadraService.deleteById(id);    	
//	    	ricettas.getIngredienti().remove(in);   	
//	    	return "redirect:/Ricette";
	    	return "redirect:/SquadreAdmin";
	    }
	 @GetMapping("admin/CancellaSquadre/{id}")
	    public String cancellaSquadraAd(@ModelAttribute("squadra")Squadra squadra,
	    	@PathVariable("id") Long id, Model model) {  
//	    	Ricetta ricettas = ricettaService.findById(id);
//	    	Ingrediente in = ingredienteService.findById(idIn);
	    	Squadra r = squadraService.findById(id);
	    	model.addAttribute("Squadra",r);
	    	if(r.getPresidente()!=null) {
	    		r.getPresidente().setSquadra(null);
	    	}
		 	squadraService.deleteById(id);    	
//	    	ricettas.getIngredienti().remove(in);   	
//	    	return "redirect:/Ricette";
	    	return "redirect:/Squadre";
	    }
	 
	 
	 
	 /*Setta un presidente in una squadra*/
	 @GetMapping("/SetPresidenteAdmin/{id}")
	    public String SetSquadraAdmin(
	    	@PathVariable("id") Long id, Model model, @ModelAttribute("Squadra")Squadra squadra) {  
//	    	Ricetta ricettas = ricettaService.findById(id);
//	    	Ingrediente in = ingredienteService.findById(idIn);
	    	Squadra s = squadraService.findById(id);
	    	Iterable<Presidente>presidenti = presidenteService.findPresidentiNonAssegnati();
	    	model.addAttribute("Squadra",s);
	    	model.addAttribute("presidenti",presidenti); 
	    	return "admin/SetPresidenteAdmin.html";
	    }
	 
	 
	 @PostMapping("/SetPresidente")
		public String SetPresidente(
		        @ModelAttribute("Squadra") Squadra squadra,@RequestParam("id")Long id,
		        @RequestParam(value="presidente", required = false) Long presidenteId,
		        Model model) {
		 	
		 	Squadra s = squadraService.findById(squadra.getId());		 	
		 	 
			if (presidenteId != null) {
		        Presidente presidente = presidenteService.findById(presidenteId);
		        s.setPresidente(presidente); // Associa il presidente selezionato alla squadra
		        presidente.setSquadra(squadra);
		        
		    } else {
		        squadra.setPresidente(null); // Nessun presidente selezionato
		    }
		    squadraService.save(s); // Salva la squadra con o senza presidente
	 
		    model.addAttribute("squadre", squadraService.findAll());
		    return "Squadre.html"; // Redirigi alla pagina che mostra tutte le squadre
		}
	 	 
	
	    
	    @GetMapping("/GiocatoriAdmin/{id}")
		public String getGiocatoriAdmin(@PathVariable("id")Long Id,Model model) {
				model.addAttribute("Giocatori", giocatoreService.findAll());
				model.addAttribute("Squadra", squadraService.findById(Id));
				return "admin/GiocatoriAdmin.html";
			}
	    
	  
	    
	    /*Elenco dei presidenti che puoi eliminare  o aggiungere*/
	    @GetMapping("/PresidentiAdmin")
		public String getPresidentiAdmin(Model model) {
	    		model.addAttribute("credentials",new Credentials());
				model.addAttribute("presidenti", presidenteService.findAll());
				return "admin/PresidentiAdmin.html";
			}
	 
	 /*Form per aggiungere un presidente*/
	    @GetMapping(value = "/formAddPresidente") 
		public String addPresidente(Model model) {
			model.addAttribute("presidente", new Presidente());
			model.addAttribute("user", new User());
			model.addAttribute("credentials", new Credentials());
			return "admin/formAddPresidente.html";
		}
	    	    
	    @PostMapping(value = { "/addPresidente" })
	    public String addNewPresidente(@ModelAttribute("presidente") Presidente presidente,
	    						    @ModelAttribute("user") User user,
	                 BindingResult userBindingResult, 
	                 @ModelAttribute("credentials") Credentials credentials,
	                 BindingResult credentialsBindingResult,
	                 Model model) {
			
			this.credentialsValidator.validate(credentials, credentialsBindingResult);
			this.userValidator.validate(user, userBindingResult);
			
			// se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
	        if(!credentialsBindingResult.hasErrors() ) {
	            
	        	 credentialsService.saveCredentials(credentials);
	            presidenteService.save(presidente);      
	            presidente.setUseri(user);
	             credentials.setUser(user);
	             user.setName(presidente.getNome());
	             user.setSurname(presidente.getCognome());
	             userService.saveUser(user);  	            
	            return "redirect:/PresidentiAdmin";
	        }
	        return "admin/formAddPresidente.html";
	    }

	 
	 
}
