package upc.edu.pe.inventoryservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import upc.edu.pe.inventoryservice.client.UserClient;
import upc.edu.pe.inventoryservice.entities.Category;
import upc.edu.pe.inventoryservice.entities.Product;
import upc.edu.pe.inventoryservice.exception.ResourceNotFoundException;
import upc.edu.pe.inventoryservice.model.User;
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
    UserClient userClient;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    @Override
    public Product save(Product entity)   {
        entity.setStatus("1");
        return productRepository.save(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            User user = userClient.fetchById(product.getUserId()).getBody();
            products.get(i).setUser(user);
        }
        return products;
    }

    @Transactional(readOnly = true)
    @Override
    public Product findById(Long aLong)  {
        Product product = productRepository.findById(aLong)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Id", aLong));
        if(product != null) {
            User user = userClient.fetchById(product.getUserId()).getBody();
            product.setUser(user);
        }
        return product;
    }

    @Override
    public Product update(Product entity)   {
        return productRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong)   {
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

    @Override
    public List<Product> getAllProductsByCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId).map(category -> {
            List<Product> products = category.getProducts();
            return products;
        }).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
    }
}
