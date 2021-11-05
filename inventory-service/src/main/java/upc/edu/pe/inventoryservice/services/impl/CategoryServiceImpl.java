package upc.edu.pe.inventoryservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import upc.edu.pe.inventoryservice.entities.Category;
import upc.edu.pe.inventoryservice.repositories.CategoryRepository;
import upc.edu.pe.inventoryservice.services.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    public CategoryRepository categoryRepository;

    @Transactional
    @Override
    public Category save(Category entity) throws Exception{
        return categoryRepository.save(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Category> findAll() throws Exception {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Category> findById(Long aLong) throws Exception {
        return categoryRepository.findById(aLong);
    }

    @Transactional
    @Override
    public Category update(Category entity) throws Exception {
        return categoryRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        categoryRepository.findById(aLong);
    }
}
