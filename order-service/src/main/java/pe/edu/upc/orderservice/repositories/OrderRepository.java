package pe.edu.upc.orderservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.orderservice.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
