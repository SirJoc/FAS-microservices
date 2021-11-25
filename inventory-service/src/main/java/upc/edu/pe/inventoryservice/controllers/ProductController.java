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
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(path = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResource> fetchById(@PathVariable("id") Long id) {
        try {
            Product product = productService.findById(id);
            if (product != null){
                return new ResponseEntity<ProductResource>(convertToResource(product) , HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/products")
    public ResponseEntity<List<ProductResource>> fetchAllProduct()   {
        List<Product> products = new ArrayList<>();
        products = productService.findAll();
        if (products.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(convertListToListResource(products));
    }

    @GetMapping(path = "/products/order-details/{id}")
    public ResponseEntity<List<Product>> fetchAllProductByOrderDetailId(@PathVariable(name = "id") Long id )   {
        List<Product> products = new ArrayList<>();
        products = productService.findAllByOdId(id);
        if (products.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @PostMapping(path = "/products")
    public ProductResource createProduct(@Valid @RequestBody SaveProductResource resource) throws Exception {
        return convertToResource(productService.save(convertToEntity(resource)));
    }

    @PutMapping("/products/{id}")
    public ProductResource updateProduct(@Valid @RequestBody SaveProductResource resource,@PathVariable(name = "id") Long id){
        Product product = convertToEntity(resource);
        return convertToResource(productService.update(id,product));
    }


    // PRODUCT-CATEGORY
    @PostMapping(path = "/assign", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResource assignProductCategory(@RequestParam(name = "productId") Long productId,
                                                 @RequestParam(name = "categoryId") Long categoryId) {
        return convertToResource(productService.assignProductCategory(productId, categoryId));
    }

    // CATEGORY-PRODUCT
    @GetMapping(path = "/categories/{categoryId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResource>> getAllProductsByCategoryId(@PathVariable Long categoryId) {
        List<Product> products = new ArrayList<>();
        products = productService.getAllProductsByCategoryId(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(convertListToListResource(products));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Long id){
        productService.deleteById(id);
        return ResponseEntity.ok("DELETED");
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
