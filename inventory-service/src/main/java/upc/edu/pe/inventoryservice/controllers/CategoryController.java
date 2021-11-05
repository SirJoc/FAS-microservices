package upc.edu.pe.inventoryservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import upc.edu.pe.inventoryservice.entities.Category;
import upc.edu.pe.inventoryservice.resource.CategoryResource;
import upc.edu.pe.inventoryservice.resource.SaveCategoryResource;
import upc.edu.pe.inventoryservice.services.CategoryService;


import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryService categoryService;

    @GetMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryResource> fetchById(@PathVariable("id") Long id) {
        try {
            Optional<Category> optionalCategory = categoryService.findById(id);
            if (optionalCategory.isPresent()){
                return new ResponseEntity<CategoryResource>(convertToResource(optionalCategory.get()) , HttpStatus.OK);
//                return new ResponseEntity<Category>(optionalCategory.get(), HttpStatus.OK);

            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoryResource>> fetchAllCategory() throws Exception {
        List<Category> categories = new ArrayList<>();
        categories = categoryService.findAll();
        if (categories.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(convertListToListResource(categories));
    }

    @PostMapping
    public CategoryResource createCategory(@Valid @RequestBody SaveCategoryResource resource) throws Exception {
        return convertToResource(categoryService.save(convertToEntity(resource)));
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
