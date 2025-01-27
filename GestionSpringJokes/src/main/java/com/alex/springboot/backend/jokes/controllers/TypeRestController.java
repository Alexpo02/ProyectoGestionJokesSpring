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

import com.alex.springboot.backend.jokes.dto.TypesDTO;
import com.alex.springboot.backend.jokes.entity.Types;
import com.alex.springboot.backend.jokes.services.IJokeService;
import com.alex.springboot.backend.jokes.services.ITypeService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class TypeRestController {
	@Autowired
	ITypeService typeService;
	
	@Autowired
	IJokeService jokeService;
	
	@GetMapping("/types")
	public ResponseEntity<?> getAllTypes() {
	    List<Types> typesList = null;
	    Map<String, Object> response = new HashMap<>();
	    try {
	        typesList = typeService.findAll();
	        if (typesList == null || typesList.isEmpty()) {
	            response.put("mensaje", "No hay tipos en la base de datos");
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }
	    } catch (DataAccessException e) {
	        response.put("mensaje", "Error al realizar la consulta en la base de datos");
	        response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    } 
	    List<TypesDTO> typesDTOList = typesList.stream().map(TypesDTO::new).collect(Collectors.toList());
	    return new ResponseEntity<>(typesDTOList, HttpStatus.OK);
	}
	
	@GetMapping("/types/{id}")
	public ResponseEntity<?> showById(@PathVariable Integer id) {
		Types type = null;
		Map<String, Object> response = new HashMap<>();
		try {
			type = typeService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (type == null) {
			response.put("mensaje", "El tipo ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		TypesDTO typesDTO = new TypesDTO(type);
		return new ResponseEntity<>(typesDTO, HttpStatus.OK);
	}
	
	@PostMapping("/types")
	public ResponseEntity<?> create(@RequestBody Types type) {
		Types typeNew = null;
		Integer id = typeService.findAll().size() + 1;
		Map<String, Object> response = new HashMap<>();
		try {
			type.setId(id);
			typeNew = typeService.save(type);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El tipo ha sido creada con éxito!");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/types/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		Types type = typeService.findById(id);
		Map<String, Object> response = new HashMap<>();
		if (type == null) {
			response.put("mensaje", "El tipo ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		
		if(type.getType().equals("single") || type.getType().equals("twopart")) {
			response.put("mensaje", "No se pueden eliminar los tipos single y twopart");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			if(type.getJokeses().size() > 0) {
				type.getJokeses().forEach(joke -> {
					jokeService.deleteById(joke.getId());
				});
			}
			typeService.deleteById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el delete en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El tipo ha sido eliminada con éxito!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/types/{id}")
	public ResponseEntity<?> update(@RequestBody Types type, @PathVariable Integer id) {
		Types typeActual = typeService.findById(id);
		Map<String, Object> response = new HashMap<>();
		if (typeActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el tipo ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		try {
			typeActual.setId(typeActual.getId());
			typeActual.setType(type.getType());
			typeService.save(typeActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el tipo en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El tipo ha sido actualizada con éxito!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
