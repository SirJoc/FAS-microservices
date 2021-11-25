package upc.edu.pe.inventoryservice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.inventoryservice.entities.Category;
import upc.edu.pe.inventoryservice.entities.Product;
import upc.edu.pe.inventoryservice.resource.CategoryResource;
import upc.edu.pe.inventoryservice.resource.ProductResource;
import upc.edu.pe.inventoryservice.resource.SaveCategoryResource;
import upc.edu.pe.inventoryservice.resource.SaveProductResource;
import upc.edu.pe.inventoryservice.services.CategoryService;


import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryService categoryService;


    @GetMapping(path = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryResource> fetchById(@PathVariable("id") Long id) {
        try {
            Category category = categoryService.findById(id);
            if (category != null){
                return new ResponseEntity<CategoryResource>(convertToResource(category) , HttpStatus.OK);

            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/categories")
    public ResponseEntity<List<CategoryResource>> fetchAllCategory()  {
        List<Category> categories = new ArrayList<>();
        categories = categoryService.findAll();
        if (categories.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(convertListToListResource(categories));
    }

    @PutMapping("/categories/{id}")
    public CategoryResource updateCategory(@Valid @RequestBody SaveCategoryResource resource, @PathVariable(name = "id") Long id){
        Category category = convertToEntity(resource);
        return convertToResource(categoryService.update(id,category));
    }

    @PostMapping(path = "/categories")
    public CategoryResource createCategory(@Valid @RequestBody SaveCategoryResource resource)   {
        return convertToResource(categoryService.save(convertToEntity(resource)));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(name = "id") Long id){
        categoryService.deleteById(id);
        return ResponseEntity.ok("DELETED");
    }

    private Category convertToEntity(SaveCategoryResource resource) {
        return mapper.map(resource, Category.class);
    }

    private CategoryResource convertToResource(Category entity) {
        return mapper.map(entity, CategoryResource.class);
    }

    private List<CategoryResource> convertListToListResource(List<Category> listEntity) {
        List<CategoryResource> result = new ArrayList<>();
        for (Category p: listEntity) {
            result.add(convertToResource(p));
        }
        return result;
    }
}
