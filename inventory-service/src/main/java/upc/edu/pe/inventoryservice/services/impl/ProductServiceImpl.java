package upc.edu.pe.inventoryservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import upc.edu.pe.inventoryservice.entities.Category;
import upc.edu.pe.inventoryservice.entities.Product;
import upc.edu.pe.inventoryservice.exception.ResourceNotFoundException;
import upc.edu.pe.inventoryservice.repositories.CategoryRepository;
import upc.edu.pe.inventoryservice.repositories.ProductRepository;
import upc.edu.pe.inventoryservice.services.ProductService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    @Override
    public Product save(Product entity) throws Exception {
        return productRepository.save(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() throws Exception {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long aLong) throws Exception {
        return productRepository.findById(aLong);
    }

    @Override
    public Product update(Product entity) throws Exception {
        return productRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        productRepository.deleteById(aLong);
    }

    @Override
    public Product assignProductCategory(Long productId, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        return productRepository.findById(productId)
                .map(product -> productRepository.save(product.categoryWith(category)))
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Id", productId));
    }

    @Override
    public Product unassignProductCategory(Long productId, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        return productRepository.findById(productId)
                .map(product -> productRepository.save(product.unCategoryWith(category)))
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Id", productId));
    }
}
