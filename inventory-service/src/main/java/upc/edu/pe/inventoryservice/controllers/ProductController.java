package upc.edu.pe.inventoryservice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upc.edu.pe.inventoryservice.entities.Product;
import upc.edu.pe.inventoryservice.resource.ProductResource;
import upc.edu.pe.inventoryservice.resource.SaveProductResource;
import upc.edu.pe.inventoryservice.services.ProductService;

import javax.validation.Valid;
import java.util.*;
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResource> fetchById(@PathVariable("id") Long id) {
        try {
            Optional<Product> optionalProduct = productService.findById(id);
            if (optionalProduct.isPresent()){
                return new ResponseEntity<ProductResource>(convertToResource(optionalProduct.get()) , HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductResource>> fetchAllProduct() throws Exception {
        List<Product> products = new ArrayList<>();
        products = productService.findAll();
        if (products.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(convertListToListResource(products));
    }

    @PostMapping
    public ProductResource createProduct(@Valid @RequestBody SaveProductResource resource) throws Exception {
        return convertToResource(productService.save(convertToEntity(resource)));
    }

    @PostMapping(path = "/id/{productId}/categories/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResource assignProductCategory(@PathVariable(name = "productId") Long productId,
                                                 @PathVariable(name = "categoryId") Long categoryId) {
        return convertToResource(productService.assignProductCategory(productId, categoryId));
    }

    private Product convertToEntity(SaveProductResource resource) {
        return mapper.map(resource, Product.class);
    }

    private ProductResource convertToResource(Product entity) {
        return mapper.map(entity, ProductResource.class);
    }

    private List<ProductResource> convertListToListResource(List<Product> listEntity) {
        List<ProductResource> result = new ArrayList<>();
        for (Product p: listEntity) {
            result.add(convertToResource(p));
        }
        return result;
    }

}
