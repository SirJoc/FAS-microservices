package pe.edu.upc.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.edu.upc.orderservice.model.Product;
import pe.edu.upc.orderservice.model.ProductResource;

import java.util.List;

@FeignClient(name = "inventory-service", fallback = InventoryHystrixFallbackFactory.class)
public interface InventoryClient {
    @GetMapping(path = "/api/products/order-details/{id}")
    public ResponseEntity<List<Product>> fetchAllProductByOrderDetailId(@PathVariable(name = "id") Long id );
}
