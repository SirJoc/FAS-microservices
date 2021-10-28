package pe.edu.upc.orderservice.services;

import org.aspectj.weaver.ast.Or;
import pe.edu.upc.orderservice.entities.Order;
import pe.edu.upc.orderservice.entities.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
    List<OrderDetail> listAllOrderDetail();
    OrderDetail getOrderDetail(Long id);
    OrderDetail getOrderDetailByOrderId(Long id);

    OrderDetail createOrderDetail(Long orderId, OrderDetail orderDetail);
    OrderDetail updateOrderDetail(Long orderId,OrderDetail orderDetail);
    OrderDetail deleteOrderDetail(Long orderId, Long orderDetailId);
    OrderDetail findByQuantity(Integer quantity);
    OrderDetail findByPrice(Integer price);
}
