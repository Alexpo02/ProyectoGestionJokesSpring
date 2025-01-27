package com.alex.springboot.backend.jokes.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

import com.alex.springboot.backend.jokes.dto.LanguageDTO;
import com.alex.springboot.backend.jokes.entity.Language;
import com.alex.springboot.backend.jokes.services.IJokeService;
import com.alex.springboot.backend.jokes.services.ILanguageService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class LanguageRestController {
	@Autowired
	ILanguageService languageService;
	
	@Autowired
	IJokeService jokeService;
	
	@GetMapping("/languages")
	public ResponseEntity<?> getAllLanguages() {
	    List<Language> languagesList = null;
	    Map<String, Object> response = new HashMap<>();
	    try {
	    	languagesList = languageService.findAll();
	        if (languagesList == null || languagesList.isEmpty()) {
	            response.put("mensaje", "No hay lenguajes en la base de datos");
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }
	    } catch (DataAccessException e) {
	        response.put("mensaje", "Error al realizar la consulta en la base de datos");
	        response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    } 
	    List<LanguageDTO> flagsDTOList = languagesList.stream().map(LanguageDTO::new).collect(Collectors.toList());
	    return new ResponseEntity<>(flagsDTOList, HttpStatus.OK);
	}
	
	@GetMapping("/languages/{id}")
	public ResponseEntity<?> showById(@PathVariable Integer id) {
		Language language = null;
		Map<String, Object> response = new HashMap<>();
		try {
			language = languageService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (language == null) {
			response.put("mensaje", "El lenguaje ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		LanguageDTO languageDTO = new LanguageDTO(language);
		return new ResponseEntity<>(languageDTO, HttpStatus.OK);
	}
	
	@PostMapping("/languages")
	public ResponseEntity<?> create(@RequestBody Language language) {
		Language languageNew = null;
		Integer id = languageService.findAll().size() + 1;
		Map<String, Object> response = new HashMap<>();
		try {
			language.setId(id);
			languageNew = languageService.save(language);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El lenguaje ha sido creada con éxito!");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/languages/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		Language language = languageService.findById(id);
		Map<String, Object> response = new HashMap<>();
		if (language == null) {
			response.put("mensaje", "El lenguaje ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		try {
			if(language.getJokeses().size() > 0) {
				language.getJokeses().forEach(joke -> {
					jokeService.deleteById(joke.getId());
				});
			}
			languageService.deleteById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el delete en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El lenguaje ha sido eliminada con éxito!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/languages/{id}")
	public ResponseEntity<?> update(@RequestBody Language language, @PathVariable Integer id) {
		Language languageActual = languageService.findById(id);
		Map<String, Object> response = new HashMap<>();
		if (languageActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el lenguaje ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		try {
			languageActual.setId(languageActual.getId());
			languageActual.setCode(language.getCode());
			languageActual.setLanguage(language.getLanguage());
			languageService.save(languageActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la flag en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La flag ha sido actualizada con éxito!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
