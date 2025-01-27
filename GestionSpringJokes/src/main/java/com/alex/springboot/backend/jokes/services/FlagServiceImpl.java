package com.alex.springboot.backend.jokes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alex.springboot.backend.jokes.dao.IFlagDAO;
import com.alex.springboot.backend.jokes.entity.Flags;

@Service
public class FlagServiceImpl implements IFlagService {

	
	@Autowired
	private IFlagDAO flagDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Flags> findAll() {
		return (List<Flags>) flagDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Flags findById(Integer id) {
		return flagDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Flags save(Flags flag) {
		return flagDao.save(flag);
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		flagDao.deleteById(id);
		
	}

	@Override
	@Transactional
	public void delete(Flags flag) {
		flagDao.delete(flag);
	}

}
