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

import com.alex.springboot.backend.jokes.dto.CategoriesDTO;
import com.alex.springboot.backend.jokes.entity.Categories;
import com.alex.springboot.backend.jokes.services.ICategoryService;
import com.alex.springboot.backend.jokes.services.IJokeService;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class CategoryRestController {
	@Autowired
	ICategoryService categoryService;
	
	@Autowired
	IJokeService jokeService;
	
	@GetMapping("/categories")
	public ResponseEntity<?> getAllCategories() {
	    List<Categories> categoriesList = null;
	    Map<String, Object> response = new HashMap<>();
	    try {
	    	categoriesList = categoryService.findAll();
	        if (categoriesList == null || categoriesList.isEmpty()) {
	            response.put("mensaje", "No hay categorias en la base de datos");
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }
	    } catch (DataAccessException e) {
	        response.put("mensaje", "Error al realizar la consulta en la base de datos");
	        response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    } 
	    List<CategoriesDTO> categoriesDTOList = categoriesList
	    		.stream().map(CategoriesDTO::new).collect(Collectors.toList());
	    
	    return new ResponseEntity<>(categoriesDTOList, HttpStatus.OK);
	}
	
	@GetMapping("/categories/{id}")
	public ResponseEntity<?> showById(@PathVariable Integer id) {
		Categories category = null;
		Map<String, Object> response = new HashMap<>();
		try {
			category = categoryService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (category == null) {
			response.put("mensaje", "La flag ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		CategoriesDTO categoryDTO = new CategoriesDTO(category);
		return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
	}
	
	@PostMapping("/categories")
	public ResponseEntity<?> create(@RequestBody Categories category) {
		Categories categoryNew = null;
		Integer id = categoryService.findAll().size() + 1;
		Map<String, Object> response = new HashMap<>();
		try {
			category.setId(id);
			categoryNew = categoryService.save(category);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La categoria ha sido creada con éxito!");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/categories/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		Categories category = categoryService.findById(id);
		Map<String, Object> response = new HashMap<>();
		if (category == null) {
			response.put("mensaje", "La categoria ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		try {
			if(category.getJokeses().size() > 0) {
				category.getJokeses().forEach(joke -> {
					jokeService.deleteById(joke.getId());
				});
			}
			categoryService.deleteById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el delete en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La categoria ha sido eliminada con éxito!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/categories/{id}")
	public ResponseEntity<?> update(@RequestBody Categories category, @PathVariable Integer id) {
		Categories categoryActual = categoryService.findById(id);
		Map<String, Object> response = new HashMap<>();
		if (categoryActual == null) {
			response.put("mensaje", "Error: no se pudo editar, la flag ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		try {
			categoryActual.setId(categoryActual.getId());
			categoryActual.setCategory(category.getCategory());
			categoryService.save(categoryActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la categoria en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La categoria ha sido actualizada con éxito!");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
