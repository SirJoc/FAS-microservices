package pe.edu.upc.orderservice.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.orderservice.entities.Order;
import pe.edu.upc.orderservice.repositories.OrderRepository;
import pe.edu.upc.orderservice.services.OrderService;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    @Override
    public List<Order> listAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order createOrder(Order order) {
        order.setStatus("CREATED");
        order.setCreateAt(new Date());

        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Order order) {
        Order orderDB = getOrder(order.getId());
        if (null == orderDB){
            return null;
        }

        //TODO: implement updates

        return orderRepository.save(orderDB);
    }

    @Override
    public Order deleteOrder(Long id) {
        Order orderDB = getOrder(id);
        if (null == orderDB){
            return null;
        }
        orderDB.setStatus("DELETED");
        return orderRepository.save(orderDB);
    }
}
