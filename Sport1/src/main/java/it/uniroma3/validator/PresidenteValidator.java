package it.uniroma3.validator;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.model.Credentials;
import it.uniroma3.model.Presidente;
import it.uniroma3.service.CredentialsService;
import it.uniroma3.service.PresidenteService;

@Component
public class PresidenteValidator implements Validator{

	
	@Autowired
	private PresidenteService presidenteService;
	
	

	@Override
	public void validate(Object o, Errors errors) {
		Presidente p = (Presidente)o;
		if (p.getNome()!=null && p.getCognome()!=null && this.presidenteService.existsByNomeAndCognome(p.getNome(),p.getCognome())) {
			errors.reject("presidente.duplicate");
		}
//		 Verifica che il campo nascita non sia nullo
				if (p.getNascita() == null) {
					errors.rejectValue("nascita", "nascita.empty", "La data di nascita è obbligatoria.");
				} 
	}


	@Override
	public boolean supports(Class<?> clazz) {
		return Presidente.class.equals(clazz);
	}
}

	
