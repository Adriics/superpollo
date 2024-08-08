package com.personal.usersocial.business.services;

import java.util.List;
import java.util.Optional;

import com.personal.usersocial.business.model.Usuario;

public interface UsuarioServices {
	
	List<Usuario> getAll();
	
	Optional<Usuario> read(Long id);
	
	Long create(Usuario usuario);
		
	void update(Usuario usuario);
	
	void delete(Long id);

}
