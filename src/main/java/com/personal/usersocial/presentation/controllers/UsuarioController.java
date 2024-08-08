package com.personal.usersocial.presentation.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.personal.usersocial.business.model.Usuario;
import com.personal.usersocial.business.services.UsuarioServices;
import com.personal.usersocial.presentation.config.PresentationException;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://127.0.0.1:3000")
public class UsuarioController {
	
	private UsuarioServices usuarioServices;
	
	public UsuarioController(UsuarioServices usuarioServices) {
		this.usuarioServices = usuarioServices;
	}
	
	@CrossOrigin("http://127.0.0.1:3000")
	@PostMapping	
	public ResponseEntity<Usuario> create(@RequestBody Usuario usuario, UriComponentsBuilder ucb) {
		
		if(usuario.getId() != null) {
			return ResponseEntity.badRequest().build();
		}
		usuarioServices.create(usuario);
		return ResponseEntity.ok().build();
	}
	
	@CrossOrigin("http://127.0.0.1:3000")
	@GetMapping
	public List<Usuario> getAll() {
		return usuarioServices.getAll();
	}
	
	@CrossOrigin("http://127.0.0.1:3000")
	@GetMapping("/{id}")
	public Optional<Usuario> getById(@PathVariable Long id){
		return usuarioServices.read(id);
	}
	
	@CrossOrigin("http://127.0.0.1:3000")
	@PutMapping("/{id}")
	public void update(@PathVariable Long id, @RequestBody Usuario usuario) {
		
		usuario.setId(id);
		
		try {
			usuarioServices.update(usuario);
		} catch(IllegalStateException e) {
				throw new PresentationException("No se encuentra el usuario" + id, HttpStatus.NOT_FOUND);
		}
	}
	
	@CrossOrigin("http://127.0.0.1:3000")
	@DeleteMapping("/{id}")
	public ResponseEntity<Usuario> deleteById(@PathVariable Long id) {
		if(id == null || usuarioServices.read(id).isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		
		usuarioServices.delete(id);
		return ResponseEntity.noContent().build();
	}
}
