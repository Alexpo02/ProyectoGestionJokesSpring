package com.alex.springboot.backend.jokes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alex.springboot.backend.jokes.dao.ILanguageDAO;
import com.alex.springboot.backend.jokes.entity.Language;

@Service
public class LanguageServiceImpl implements ILanguageService {
	@Autowired
	private ILanguageDAO languageDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Language> findAll() {
		return (List<Language>) languageDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Language findById(Integer id) {
		return languageDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Language save(Language flag) {
		return languageDao.save(flag);
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		languageDao.deleteById(id);
		
	}

	@Override
	@Transactional
	public void delete(Language flag) {
		languageDao.delete(flag);
	}
}
