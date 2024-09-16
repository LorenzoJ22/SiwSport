package it.uniroma3.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.model.User;
import it.uniroma3.repository.UserRepository;

@Service

public class UserService {

	@Autowired
	private UserRepository userRepository;
	

	public User getUser(Long id) {
		return userRepository.findUserById(id);
	}
	
	public User saveUser(User user) {
	   return userRepository.save(user);
	}
	
	public Boolean existsByNameAndSurname(String name, String surname) {
		return userRepository.existsByNameAndSurname(name,surname);
	}
	
	}
