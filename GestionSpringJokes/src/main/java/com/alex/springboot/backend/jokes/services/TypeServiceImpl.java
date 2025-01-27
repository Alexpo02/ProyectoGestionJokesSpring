package com.alex.springboot.backend.jokes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alex.springboot.backend.jokes.dao.ITypeDAO;
import com.alex.springboot.backend.jokes.entity.Types;


@Service
public class TypeServiceImpl implements ITypeService {
	@Autowired
	private ITypeDAO typeDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Types> findAll() {
		return (List<Types>) typeDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Types findById(Integer id) {
		return typeDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Types save(Types type) {
		return typeDao.save(type);
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		typeDao.deleteById(id);
		
	}

	@Override
	@Transactional
	public void delete(Types flag) {
		typeDao.delete(flag);
	}
}
