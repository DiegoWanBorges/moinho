package com.twokeys.moinho.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.twokeys.moinho.entities.User;
import com.twokeys.moinho.repositories.UserRepository;


@Service
public class UserService implements UserDetailsService {
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository repository;
		
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByEmail(username);
		if (user==null) {
			logger.error("Usuario não encontrado: " + username);
			throw new UsernameNotFoundException("Usuario não encontrado: " + username);
		}
		logger.info("Usuario encontrado: " + username);
		return user;
	}
		
}
