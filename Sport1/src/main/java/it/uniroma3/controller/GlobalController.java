package it.uniroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.model.Credentials;
import it.uniroma3.service.CredentialsService;

@ControllerAdvice
public class GlobalController {
    
	
	
      @ModelAttribute("userDetails")
      public UserDetails getUser() {
        UserDetails user = null;
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
          user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return user;
      }
      
//      @ModelAttribute("idUser")
//      public Long getCredential() {
//    	  UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
//			Long idUser = null;
//        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!(authentication instanceof AnonymousAuthenticationToken)) {
//        	idUser = credentials.getUser().getId();
//        }
//        
//        return idUser;
//      }
      
      
      
    }

