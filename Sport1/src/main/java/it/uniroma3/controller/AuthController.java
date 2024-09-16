package it.uniroma3.controller;

import org.springframework.stereotype.Controller;






import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.validation.BindingResult;

import it.uniroma3.model.Credentials;

import it.uniroma3.model.Presidente;

import it.uniroma3.model.User;
import it.uniroma3.service.*;
import it.uniroma3.validator.CredentialsValidator;
import it.uniroma3.validator.PresidenteValidator;
import it.uniroma3.validator.UserValidator;


@Controller
public class AuthController {
	
	 @Autowired
	 private UserService userService;
	@Autowired SquadraService squadraService;
	@Autowired
	PresidenteService presidenteService;
	@Autowired
	CredentialsService credentialsService;

	@Autowired 
	private CredentialsValidator credentialsValidator;
	@Autowired 
	private PresidenteValidator presidenteValidator;
	@Autowired
	private UserValidator userValidator;
	
	
	@GetMapping("/SceltaRegistrazione")
	public String sceltaRegistrazione(Model model) {
		return "SceltaRegistrazione.html";
	}
	
	
	@GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("presidente", new Presidente());
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "register.html";
	}
	
	
    @GetMapping("/login")
    public String showLoginForm(Model model) {
//        model.addAttribute("Credentials", new Credentials());
        return "login.html";
    }
    
    @GetMapping(value = "/") 
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
	        return "index.html";
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				model.addAttribute("userId", credentials.getUser().getId());
				model.addAttribute("users", credentials.getUser());
				return "admin/indexAdmin.html";
			}
			if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
				model.addAttribute("presidente", presidenteService.findPresidenteByUserId(credentials.getUser().getId()));
				model.addAttribute("Squadra",presidenteService.findPresidenteByUserId(credentials.getUser().getId()).getSquadra());
				model.addAttribute("userId", credentials.getUser().getId());
				model.addAttribute("users", credentials.getUser());
				return "pres/indexPres.html";
			}
			if(credentials.getRole().equals(null)) {
				return "index.html";
			}
		}
        return "index.html";
	}
    
    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
    		model.addAttribute("userId", credentials.getUser().getId());
    		model.addAttribute("users", credentials.getUser());
            return "admin/indexAdmin.html";
        }
    	if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
    		model.addAttribute("presidente", presidenteService.findPresidenteByUserId(credentials.getUser().getId()));
    		model.addAttribute("Squadra",presidenteService.findPresidenteByUserId(credentials.getUser().getId()).getSquadra());
    		model.addAttribute("userId", credentials.getUser().getId());
    		model.addAttribute("users", credentials.getUser());
            return "pres/indexPres.html";
    	}
        return "index.html";
    }
    
    @PostMapping(value = { "/register" })
    public String registerUser( @ModelAttribute("presidente") Presidente presidente,
    						    @ModelAttribute("user") User user, 
                 BindingResult userBindingResult,BindingResult presidenteBindingResult, 
                 @ModelAttribute("credentials") Credentials credentials,
                 BindingResult credentialsBindingResult,
                 Model model) {
    	
		this.credentialsValidator.validate(credentials, credentialsBindingResult);
		this.userValidator.validate(user, userBindingResult);
		this.presidenteValidator.validate(presidente,presidenteBindingResult);
		
		// se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        if(!credentialsBindingResult.hasErrors() && !presidenteBindingResult.hasErrors()) {
//        	userService.saveUser(user);
//        	credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            /*servono i hasError nell'html per tutti?*/
           presidenteService.save(presidente);
           presidente.setUseri(user);
            System.out.println("Presidente: "+presidente);
//            presidente.setCognome(user.getSurname());
//            presidente.setNome(user.getName());
//           
            credentials.setUser(user);
            user.setName(presidente.getNome());
            user.setSurname(presidente.getCognome());
           
            userService.saveUser(user);
            
            return "login.html";
        }
        return "register.html";
    }
      

   
}