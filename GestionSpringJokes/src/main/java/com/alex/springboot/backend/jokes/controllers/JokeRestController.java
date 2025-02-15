package com.alex.springboot.backend.jokes.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alex.springboot.backend.jokes.dto.JokeDTO;
import com.alex.springboot.backend.jokes.entity.Jokes;
import com.alex.springboot.backend.jokes.entity.PrimeraVez;
import com.alex.springboot.backend.jokes.entity.Telefonos;
import com.alex.springboot.backend.jokes.services.IJokeService;


@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class JokeRestController {

	@Autowired
	IJokeService jokeService;
	
	@GetMapping("/jokes/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Jokes joke = null;
        Map<String, Object> response = new HashMap<>();
        try {
            joke = jokeService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (joke == null) {
            response.put("mensaje", "La joke ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        JokeDTO jokeDTO = new JokeDTO(joke);
        return new ResponseEntity<>(jokeDTO, HttpStatus.OK);
    }
	
	@GetMapping("/jokes")
    public ResponseEntity<?> getAllJokes() {
        List<Jokes> jokesList = null;
        Map<String, Object> response = new HashMap<>();
        try {
            jokesList = jokeService.findAll();
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        if (jokesList == null || jokesList.isEmpty()) {
            response.put("mensaje", "No hay bromas en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        
        
		List<JokeDTO> jokesDTOList = jokesList.stream().map(JokeDTO::new).collect(Collectors.toList());

        return new ResponseEntity<>(jokesDTOList, HttpStatus.OK);
    }
	
	@PostMapping("/jokes")
	public ResponseEntity<?> crear(@RequestBody Jokes joke, BindingResult result){
		Integer id = jokeService.findAll().size() + 1;
		Jokes jokeNuevo = null;
        Map<String, Object> response = new HashMap<>();
        
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
        try {
        	joke.setId(id);
            jokeNuevo = jokeService.save(joke);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la inserción en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "La broma ha sido creada con éxito");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/jokes/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		Jokes joke = jokeService.findById(id);
		Map<String, Object> response = new HashMap<>();

		if (joke == null) {
			response.put("mensaje", "La broma ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		try {
			jokeService.deleteById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la eliminación en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La broma ha sido eliminada con éxito");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/jokes/{id}")
	public ResponseEntity<?> editar(@RequestBody Jokes joke, BindingResult result, @PathVariable Integer id) {
		
		Jokes jokeActual = jokeService.findById(id);
		Jokes jokeActualizado = null;
		Map<String, Object> response = new HashMap<>();

		if (jokeActual == null) {
			response.put("mensaje", "Error: no se pudo editar, la broma ID: ".concat(id.toString())
					.concat(" no existe en la base de datos!"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			jokeActual.setId(jokeActual.getId());
			jokeActual.setCategories(joke.getCategories());
			jokeActual.setLanguage(joke.getLanguage());
			jokeActual.setTypes(joke.getTypes());
			jokeActual.setText1(joke.getText1());
			jokeActual.setText2(joke.getText2());
			jokeActual.setFlagses(joke.getFlagses());
			jokeActualizado = jokeService.save(jokeActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la actualización en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La broma ha sido actualizada con éxito");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PostMapping
	public ResponseEntity<?> anyadirPrimeraVez(@RequestBody Jokes joke, PrimeraVez primeraVez){
		return null;
		
	}
	
	
	
	// Obtener una primera vez junto con los teléfonos asociados
    @GetMapping("/primera_vez/{id}")
    public ResponseEntity<?> getPrimeraVezWithTelefonos(@PathVariable Integer id) {
        PrimeraVez primeraVez = jokeService.findPrimeraVezByJokeId(id);
        Map<String, Object> response = new HashMap<>();

        if (primeraVez == null) {
            response.put("mensaje", "La primera vez de la broma ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        List<Telefonos> telefonos = jokeService.findTelefonosByPrimeraVezId(primeraVez.getId());
        response.put("primera_vez", primeraVez);
        response.put("telefonos", telefonos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Eliminar primera vez y los teléfonos asociados
    @DeleteMapping("/primera_vez/{id}")
    public ResponseEntity<?> deletePrimeraVez(@PathVariable Integer id) {
        PrimeraVez primeraVez = jokeService.findPrimeraVezByJokeId(id);
        Map<String, Object> response = new HashMap<>();

        if (primeraVez == null) {
            response.put("mensaje", "La primera vez ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            // Eliminar los teléfonos asociados
            List<Telefonos> telefonos = jokeService.findTelefonosByPrimeraVezId(primeraVez.getId());
            for (Telefonos telefono : telefonos) {
                jokeService.deleteTelefonoById(telefono.getId());
            }
            // Eliminar la entrada de PrimeraVez
            jokeService.deletePrimeraVezById(primeraVez.getId());
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar la primera vez y los teléfonos asociados");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La primera vez y los teléfonos han sido eliminados con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Modificar tanto la primera vez como sus teléfonos asociados
    @PutMapping("/primera_vez/{id}")
    public ResponseEntity<?> editarPrimeraVez(@RequestBody PrimeraVez primeraVez, @PathVariable Integer id) {
        PrimeraVez primeraVezActual = jokeService.findPrimeraVezByJokeId(id);
        Map<String, Object> response = new HashMap<>();

        if (primeraVezActual == null) {
            response.put("mensaje", "La primera vez ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            // Actualizar los datos de PrimeraVez
            primeraVezActual.setPrograma(primeraVez.getPrograma());
            primeraVezActual.setFechaEmision(primeraVez.getFechaEmision());
            jokeService.savePrimeraVez(primeraVezActual);

            // Ahora, se actualizan los teléfonos asociados
            // Si se necesita actualizar la lista de teléfonos, se puede eliminar primero los existentes y agregar los nuevos
            List<Telefonos> telefonosActualizados = primeraVez.getTelefonos();  // Los teléfonos vienen del cuerpo de la petición

            // Eliminar los teléfonos antiguos (si se requiere)
            List<Telefonos> telefonosExistentes = jokeService.findTelefonosByPrimeraVezId(primeraVezActual.getId());
            for (Telefonos telefono : telefonosExistentes) {
                jokeService.deleteTelefonoById(telefono.getId());
            }

            // Agregar los nuevos teléfonos
            for (Telefonos telefono : telefonosActualizados) {
                telefono.setPrimeraVez(primeraVezActual);  // Asegurarse de que el teléfono se asocie correctamente con la "PrimeraVez"
                jokeService.saveTelefono(telefono);  // Guardar o actualizar el teléfono
            }

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar la primera vez y sus teléfonos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La primera vez y los teléfonos han sido actualizados con éxito");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
