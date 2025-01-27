package com.alex.springboot.backend.jokes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alex.springboot.backend.jokes.dao.ICategoryDAO;
import com.alex.springboot.backend.jokes.entity.Categories;

@Service
public class CategoryServiceImpl implements ICategoryService {
	@Autowired
	private ICategoryDAO categoryDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Categories> findAll() {
		return (List<Categories>) categoryDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Categories findById(Integer id) {
		return categoryDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Categories save(Categories flag) {
		return categoryDao.save(flag);
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		categoryDao.deleteById(id);
		
	}

	@Override
	@Transactional
	public void delete(Categories category) {
		categoryDao.delete(category);
	}
}
