package pe.edu.upc.orderservice.services;

import org.aspectj.weaver.ast.Or;
import pe.edu.upc.orderservice.entities.Order;

import java.util.List;

public interface OrderService {
    List<Order> listAllOrders();
    Order getOrder(Long id);

    Order createOrder(Order order);
    Order updateOrder(Order order);
    Order deleteOrder(Long id);
}
