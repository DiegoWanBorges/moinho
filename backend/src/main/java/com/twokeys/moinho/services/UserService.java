package com.twokeys.moinho.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twokeys.moinho.dto.RoleDTO;
import com.twokeys.moinho.dto.UserDTO;
import com.twokeys.moinho.dto.UserInsertDTO;
import com.twokeys.moinho.dto.UserUpdateDTO;
import com.twokeys.moinho.entities.Role;
import com.twokeys.moinho.entities.User;
import com.twokeys.moinho.repositories.RoleRepository;
import com.twokeys.moinho.repositories.UserRepository;
import com.twokeys.moinho.services.exceptions.DatabaseException;
import com.twokeys.moinho.services.exceptions.ResourceNotFoundException;

@Service
public class UserService implements UserDetailsService {
	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository repository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;

	@Transactional(readOnly = true)
	public List<UserDTO> findByName(String name) {
		String nameConcat = "%" + name + "%";
		List<User> list = repository.findByNameLikeIgnoreCase(nameConcat);
		return list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
	}
	@Transactional(readOnly=true)
	public Page<UserDTO> findAllPaged(String name,PageRequest pageRequest){
	String nameConcat ="%"+name+"%";
	Page<User> list = repository.findByNameLikeIgnoreCase(nameConcat,pageRequest);
		return list.map(x -> new UserDTO(x));
	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

		return new UserDTO(entity);
	}

	@Transactional
	public UserDTO insert(UserInsertDTO dto) {
		User entity = new User();
		copyDtoToEntity(dto, entity);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		return new UserDTO(repository.save(entity));
	}
	
	@Transactional
	public UserDTO update(Long id, UserUpdateDTO dto) {
		User entity = repository.findById(id).get();
		
		logger.info(entity.getEmail());
		logger.info(dto.getEmail());
	
		if (entity.getEmail().equals(dto.getEmail())==false)  {
			if (repository.findByEmail(dto.getEmail()) != null) { 
				throw new ValidationException("Email j?? cadastrado");
			}
		}
		copyDtoToEntity(dto, entity);
		logger.info(dto.getPassword());
		if (dto.isUpdatePassword()) {
			entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		}
		return new UserDTO(repository.save(entity));
	}
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}

	}

	private void copyDtoToEntity(UserDTO dto, User user) {
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.getRoles().clear();
		for (RoleDTO rolDTO : dto.getRoles()) {
			Role role = roleRepository.getOne(rolDTO.getId());
			user.getRoles().add(role);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByEmail(username);
		if (user == null) {
			logger.error("Usuario n??o encontrado: " + username);
			throw new UsernameNotFoundException("Usuario n??o encontrado: " + username);
		}
		logger.info("Usuario encontrado: " + username);
		return user;
	}
	

}
