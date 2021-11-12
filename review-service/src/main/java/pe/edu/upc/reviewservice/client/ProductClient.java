package pe.edu.upc.reviewservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.edu.upc.reviewservice.model.Product;

@FeignClient(name = "inventory-service", fallback = ProductHystrixFallbackFactory.class)
public interface ProductClient {
    @GetMapping(value = "api/products/{id}")
    ResponseEntity<Product> getProduct(@PathVariable("id") long id);
}
