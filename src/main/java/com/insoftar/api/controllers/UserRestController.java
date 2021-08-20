package com.insoftar.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.insoftar.api.models.dao.IUsuarioDao;
import com.insoftar.api.models.entity.Gusto;
import com.insoftar.api.models.entity.Usuario;
import com.insoftar.api.models.service.IUsuarioService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class UserRestController {

	private final Logger log = LoggerFactory.getLogger(UserRestController.class);
	static final String SUCCESS = "success";
	static final String MENSAJE_OK = "ok";
	static final String MENSAJE_FAILED = "failed";

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IUsuarioDao usuarioDao;

	@GetMapping("/v1/usuarios")
	public List<Usuario> index() {
		return usuarioService.findAll();
	}
	
	@GetMapping("/v1/gustos")
	public List<Gusto> findAllGustos() {
		return usuarioDao.findAllGustos();
	}

	@GetMapping("/v1/usuarios/page/{page}")
	public Page<Usuario> all(@PathVariable Integer page) {
		return usuarioService.findAll(PageRequest.of(page, 10));
	}
	
	@GetMapping("/v1/usuarios/{id}")
	public Usuario findByUser(@PathVariable Long id) {
		return usuarioDao.findId(id);
	}


	@PostMapping("/v1/usuarios")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario, BindingResult result) {
		Usuario user = null;
		Map<String, Object> response = new HashMap<>();

		List<String> errors = this.errors(result);

		if (result.hasErrors()) {
			response.put("error", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		Pattern pat = Pattern.compile("[a-zA-Z ]{2,254}");   
	    Matcher mat = pat.matcher(usuario.getCedula().toString());
	    
	    if(mat.find()){
	    	response.put("error", "campo cedula no puede contener letras " + usuario.getCedula());
	    	response.put("cedula", true);
	    	return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST); 
	    }
	    
	    try {
			user = usuarioDao.findByCedulaOrCorreo(usuario.getCedula(), usuario.getCorreo());
			if (user != null) {
				
				String userCedula = usuario.getCedula();
				
				if(usuario.getCedula().equals(userCedula)) {
					response.put("error", "Ya existe la cedula: " + usuario.getCedula());
					response.put("cedula", true);
					response.put("correo", false);
				}
				
				String userCorreo = user.getCorreo();
				if(usuario.getCorreo().equals(userCorreo)) {
					response.put("error", "Ya existe el correo: " + usuario.getCorreo());
					response.put("cedula", false);
					response.put("correo", true);
				}
					
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (DataAccessException e) {
			response.put("error", "Ocurrio un error: ".concat(e.getCause().getMessage().concat(": ")
					.concat(e.getMostSpecificCause().getMessage().concat(e.toString()))));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try {
			user = usuarioService.save(usuario);
		} catch (DataAccessException e) {
			response.put("error", "Ocurrio un error: ".concat(e.getCause().getMessage().concat(": ")
					.concat(e.getMostSpecificCause().getMessage().concat(e.toString()))));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put(SUCCESS, MENSAJE_OK);
		response.put("data", user);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	
	@PutMapping("/v1/usuarios")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, BindingResult result) {
		Usuario user = null;
		Map<String, Object> response = new HashMap<>();

		List<String> errors = this.errors(result);

		if (result.hasErrors()) {
			response.put("error", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		Pattern pat = Pattern.compile("[a-zA-Z ]{2,254}");   
	    Matcher mat = pat.matcher(usuario.getCedula().toString());
	    
	    if(mat.find()){
	    	response.put("error", "campo cedula no puede contener letras " + usuario.getCedula());
	    	response.put("cedula", true);
	    	return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST); 
	    }
	    
		try {
			user = usuarioService.save(usuario);
		} catch (DataAccessException e) {
			response.put("error", "Ocurrio un error: ".concat(e.getCause().getMessage().concat(": ")
					.concat(e.getMostSpecificCause().getMessage().concat(e.toString()))));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put(SUCCESS, MENSAJE_OK);
		response.put("data", user);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	public List<String> errors(BindingResult result) {
		List<String> errors = result.getFieldErrors().stream()
				.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
				.collect(Collectors.toList());
		return errors;
	}

}
