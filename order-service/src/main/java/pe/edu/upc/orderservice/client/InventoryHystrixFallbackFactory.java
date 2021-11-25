package pe.edu.upc.orderservice.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pe.edu.upc.orderservice.model.Product;
import pe.edu.upc.orderservice.model.ProductResource;

import java.util.ArrayList;
import java.util.List;

@Component
public class InventoryHystrixFallbackFactory implements InventoryClient{
    @Override
    public ResponseEntity<List<Product>> fetchAllProductByOrderDetailId(Long id) {
        List<Product> productResources = new ArrayList<>();
        return ResponseEntity.ok(productResources);
    }
}
