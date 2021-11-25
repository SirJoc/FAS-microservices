package pe.edu.upc.orderservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.orderservice.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
