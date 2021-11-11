package pe.edu.upc.reviewservice.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pe.edu.upc.reviewservice.model.Product;
import pe.edu.upc.reviewservice.model.User;

@Component
public class ProductHystrixFallbackFactory implements ProductClient{
    @Override
    public ResponseEntity<Product> getProduct(long id) {
        Product product = Product.builder()
                .name("none")
                .corporation("none")
                .price(0.0).build();
        return ResponseEntity.ok(product);
    }
}
