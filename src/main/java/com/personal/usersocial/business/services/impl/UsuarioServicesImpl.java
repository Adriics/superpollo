package com.personal.usersocial.business.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.personal.usersocial.business.model.Usuario;
import com.personal.usersocial.business.services.UsuarioServices;
import com.personal.usersocial.integration.model.UsuarioPL;
import com.personal.usersocial.integration.repositories.UsuarioPLRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServicesImpl implements UsuarioServices {

	private UsuarioPLRepository usuarioPLRepository;
	private DozerBeanMapper mapper;
	
	public UsuarioServicesImpl(UsuarioPLRepository usuarioPLRepository, DozerBeanMapper mapper) {
		this.usuarioPLRepository = usuarioPLRepository;
		this.mapper = mapper;
	}
	
	@Override
	@Transactional
	public Long create(Usuario usuario) {
		
		if(usuario.getId() != null) {
			throw new IllegalStateException("Para crear un usuario, el id tiene que ser null!");
		}
		
		UsuarioPL usuarioPL = mapper.map(usuario, UsuarioPL.class);

		return usuarioPLRepository.save(usuarioPL).getId();
		
	}


	@Override
	@Transactional
	public void update(Usuario usuario) {

		boolean existe = usuarioPLRepository.existsById(usuario.getId());
		
		if(!existe) {
			throw new IllegalStateException("No se puede actualizar el usuario " + usuario.getId() + ". No existe");
		}
		
		usuarioPLRepository.save(mapper.map(usuario, UsuarioPL.class));
		
	}

	@Override
	@Transactional
	public void delete(Long id) {
		boolean existe = usuarioPLRepository.existsById(id);
		
		if(!existe) {
			throw new IllegalStateException("No se puede eliminar el usuario " + id + ". No existe");
		}
		
		usuarioPLRepository.deleteById(id);
	}

	@Override
	public List<Usuario> getAll() {
		List<UsuarioPL> usuariosPL = usuarioPLRepository.findAll();
		List<Usuario> usuarios = new ArrayList<>();
		
		for(UsuarioPL usuarioPL: usuariosPL) {
			usuarios.add(mapper.map(usuarioPL, Usuario.class));
		}
		
		return usuarios;
	}

	@Override
	
	public Optional<Usuario> read(Long id) {
		
		Optional<UsuarioPL> usuarioPL = usuarioPLRepository.findById(id);
		Usuario usuario = null;
		
		if(usuarioPL.isPresent()) {
			usuario = mapper.map(usuarioPL.get(), Usuario.class);
		}
		
		return Optional.ofNullable(usuario);
	}

}
