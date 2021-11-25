package pe.edu.upc.orderservice.services.impls;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.orderservice.client.InventoryClient;
import pe.edu.upc.orderservice.entities.Order;
import pe.edu.upc.orderservice.entities.OrderDetail;
import pe.edu.upc.orderservice.model.Product;
import pe.edu.upc.orderservice.model.ProductResource;
import pe.edu.upc.orderservice.repositories.OrderDetailRepository;
import pe.edu.upc.orderservice.repositories.OrderRepository;
import pe.edu.upc.orderservice.services.OrderDetailService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    InventoryClient inventoryClient;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<OrderDetail> listAllOrderDetail() {
        return orderDetailRepository.findAll();
    }

    @Override
    public OrderDetail getOrderDetail(Long id) {
        return orderDetailRepository.findById(id).orElse(null);
    }

    @Override
    public OrderDetail getOrderDetailByOrderId(Long id) {
        return orderDetailRepository.findByOrderId(id);
    }

    @Override
    public OrderDetail createOrderDetail(Long orderId, OrderDetail orderDetail) {
        Order orderDB = orderRepository.findById(orderId).orElse(null);

        if (null == orderDB){
            return null;
        }

        orderDetail.setStatus("CREATED");
        orderDetail.setCreateAt(new Date());
        orderDB.setOrderDetail(orderDetail);
        List<Product> products = inventoryClient.fetchAllProductByOrderDetailId(orderId).getBody();
        orderDetail.setProducts(products);
        orderDetailRepository.save(orderDetail);
        orderRepository.save(orderDB);
        return orderDetail;
    }

    @Override
    public OrderDetail updateOrderDetail(Long orderId, OrderDetail orderDetail) {

        Order orderDB = orderRepository.findById(orderId).orElse(null);
        if (null == orderDB){
            System.out.println("EL QUE FALLA ES ORDER");
            return null;
        }

        OrderDetail orderDetailDB = orderDetailRepository.findById(orderDB.getOrderDetail().getId()).get();
        if (null == orderDetailDB){
            System.out.println("EL QUE FALLA ES ORDERDETAIL");
            return null;
        }
        orderDetailDB.setDescription(orderDetail.getDescription());
        orderDetailDB.setPrice(orderDetail.getPrice());
        orderDetailDB.setQuantity(orderDetail.getQuantity());
        orderDetailDB.setStatus(orderDetail.getStatus());
        orderDetailRepository.save(orderDetailDB);
        orderDB.setOrderDetail(orderDetailDB);
        orderRepository.save(orderDB);
        return orderDetailDB;
    }

    @Override
    public OrderDetail deleteOrderDetail(Long orderId, Long orderDetailId) {
        Order orderDB = orderRepository.findById(orderId).orElse(null);
        if (null == orderDB){
            return null;
        }

        OrderDetail orderDetailDB = getOrderDetail(orderDetailId);
        if (null == orderDetailDB){
            return null;
        }

        orderDetailDB.setStatus("DELETED");
        orderRepository.save(orderDB);
        return orderDetailRepository.save(orderDetailDB);
    }

    @Override
    public OrderDetail findByQuantity(Integer quantity) {
        return orderDetailRepository.findByQuantity(quantity);
    }

    @Override
    public OrderDetail findByPrice(Integer price) {
        return orderDetailRepository.findByPrice(price);
    }

}