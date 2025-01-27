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

import com.alex.springboot.backend.jokes.dto.FlagDTO;
import com.alex.springboot.backend.jokes.entity.Flags;
import com.alex.springboot.backend.jokes.services.IFlagService;
import com.alex.springboot.backend.jokes.services.IJokeService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class FlagRestController {
	
	@Autowired
	IFlagService flagService;
	
	@Autowired
	IJokeService jokeService;
	
	@GetMapping("/flags")
	public ResponseEntity<?> getAllFlags() {
	    List<Flags> flagsList = null;
	    Map<String, Object> response = new HashMap<>();
	    try {
	        flagsList = flagService.findAll();
	        if (flagsList == null || flagsList.isEmpty()) {
	            response.put("mensaje", "No hay flags en la base de datos");
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }
	    } catch (DataAccessException e) {
	        response.put("mensaje", "Error al realizar la consulta en la base de datos");
	        response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    } 
	    List<FlagDTO> flagsDTOList = flagsList.stream().map(FlagDTO::new).collect(Collectors.toList());
	    return new ResponseEntity<>(flagsDTOList, HttpStatus.OK);
	}
	
	@GetMapping("/flags/{id}")
	public ResponseEntity<?> showById(@PathVariable Integer id) {
		Flags flag = null;
		Map<String, Object> response = new HashMap<>();
		try {
			flag = flagService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (flag == null) {
			response.put("mensaje", "La flag ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		FlagDTO flagDTO = new FlagDTO(flag);
		return new ResponseEntity<>(flagDTO, HttpStatus.OK);
	}
	
	@PostMapping("/flags")
	public ResponseEntity<?> create(@RequestBody Flags flag) {
		Flags flagNew = null;
		Integer id = flagService.findAll().size() + 1;
		Map<String, Object> response = new HashMap<>();
		try {
			flag.setId(id);
			flagNew = flagService.save(flag);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La flag ha sido creada con éxito!");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/flags/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		Flags flag = flagService.findById(id);
		Map<String, Object> response = new HashMap<>();
		if (flag == null) {
			response.put("mensaje", "La flag ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		try {
			if(flag.getJokeses().size() > 0) {
				flag.getJokeses().forEach(joke -> {
					jokeService.deleteById(joke.getId());
				});
			}
			flagService.deleteById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el delete en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La flag ha sido eliminada con éxito!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/flags/{id}")
	public ResponseEntity<?> update(@RequestBody Flags flag, @PathVariable Integer id) {
		Flags flagActual = flagService.findById(id);
		Map<String, Object> response = new HashMap<>();
		if (flagActual == null) {
			response.put("mensaje", "Error: no se pudo editar, la flag ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		try {
			flagActual.setId(flagActual.getId());
			flagActual.setFlag(flag.getFlag());
			flagActual.setJokeses(flag.getJokeses());
			flagService.save(flagActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la flag en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La flag ha sido actualizada con éxito!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
