package upc.edu.pe.inventoryservice.services;

import upc.edu.pe.inventoryservice.entities.Category;
import upc.edu.pe.inventoryservice.entities.Product;

import java.util.List;


public interface ProductService extends CrudService<Product, Long> {
    Product assignProductCategory(Long productId, Long categoryId);
    Product unassignProductCategory(Long productId, Long categoryId);
    List<Product> getAllProductsByCategoryId(Long categoryId);
}
