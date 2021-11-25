package pe.edu.upc.orderservice.services.impls;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.orderservice.client.InventoryClient;
import pe.edu.upc.orderservice.entities.Order;
import pe.edu.upc.orderservice.model.Product;
import pe.edu.upc.orderservice.repositories.OrderRepository;
import pe.edu.upc.orderservice.services.OrderService;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private  OrderRepository orderRepository;
    @Autowired
    InventoryClient inventoryClient;
    @Override
    public List<Order> listAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        for (int i = 0; i < orderList.size(); i++) {
            List<Product> products = inventoryClient.fetchAllProductByOrderDetailId(orderList.get(i).getOrderDetail().getId()).getBody();
            orderList.get(i).getOrderDetail().setProducts(products);
        }
        return orderList;
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

        orderDB.setCreateAt(order.getCreateAt());
        orderDB.setStatus(order.getStatus());

        return orderRepository.save(orderDB);
    }

    @Override
    public Order deleteOrder(Long id) {
        Order orderDB = getOrder(id);
        if (null == orderDB){
            return null;
        }
        orderDB.setStatus("DELETED");
        orderRepository.deleteById(id);
        return orderDB;
    }
}
