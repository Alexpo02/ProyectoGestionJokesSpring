package com.alex.springboot.backend.jokes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alex.springboot.backend.jokes.dao.IJokeDAO;
import com.alex.springboot.backend.jokes.dao.IPrimeraVezDAO;
import com.alex.springboot.backend.jokes.dao.ITelefonosDAO;
import com.alex.springboot.backend.jokes.entity.Jokes;
import com.alex.springboot.backend.jokes.entity.PrimeraVez;
import com.alex.springboot.backend.jokes.entity.Telefonos;

@Service
public class JokeServiceImpl implements IJokeService {
	
	@Autowired
	private IJokeDAO jokeDao;
	
	@Autowired
	private IPrimeraVezDAO primeraVezDao;
	
	@Autowired
	private ITelefonosDAO telefonosDao;

	@Override
	@Transactional(readOnly = true)
	public List<Jokes> findAll() {
		return (List<Jokes>) jokeDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Jokes findById(Integer id) {
		return jokeDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Jokes save(Jokes cliente) {
		return jokeDao.save(cliente);
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		jokeDao.deleteById(id);
		
	}

	@Override
	@Transactional
	public void delete(Jokes cliente) {
		jokeDao.delete(cliente);
	}
	
	// Nueva funcionalidad para trabajar con la tabla "PrimeraVez"

    @Transactional(readOnly = true)
    public PrimeraVez findPrimeraVezByJokeId(Integer jokeId) {
        // Busca si existe una entrada en "PrimeraVez" asociada a un chiste (joke)
        return primeraVezDao.findByJokeId(jokeId);
    }

    @Transactional
    public PrimeraVez savePrimeraVez(PrimeraVez primeraVez) {
        return primeraVezDao.save(primeraVez);
    }

    // Nueva funcionalidad para trabajar con la tabla "Telefonos"
    
    @Transactional(readOnly = true)
    public List<Telefonos> findTelefonosByPrimeraVezId(Integer primeraVezId) {
        return telefonosDao.findByPrimeraVezId(primeraVezId);
    }

    @Transactional
    public Telefonos saveTelefono(Telefonos telefono) {
        return telefonosDao.save(telefono);
    }
    
    @Override
	@Transactional
	public void deleteTelefonoById(Integer id) {
		telefonosDao.deleteById(id);
	}
    
    @Override
	@Transactional
	public void deletePrimeraVezById(Integer id) {
		primeraVezDao.deleteById(id);
	}

}
