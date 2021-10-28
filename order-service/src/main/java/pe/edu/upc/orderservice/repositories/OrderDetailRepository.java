package pe.edu.upc.orderservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.orderservice.entities.OrderDetail;

import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    OrderDetail findByOrderId(Long orderId);
    OrderDetail findByQuantity(Integer quantity);
    OrderDetail findByPrice(Integer price);
}
