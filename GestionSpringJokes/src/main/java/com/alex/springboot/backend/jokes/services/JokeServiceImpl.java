package com.alex.springboot.backend.jokes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alex.springboot.backend.jokes.dao.IJokeDAO;
import com.alex.springboot.backend.jokes.entity.Jokes;

@Service
public class JokeServiceImpl implements IJokeService {
	
	@Autowired
	private IJokeDAO jokeDao;

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

}
