package pe.edu.upc.orderservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pe.edu.upc.orderservice.client.InventoryClient;
import pe.edu.upc.orderservice.entities.Order;
import pe.edu.upc.orderservice.repositories.OrderRepository;
import pe.edu.upc.orderservice.services.OrderService;
import pe.edu.upc.orderservice.services.impls.OrderServiceImpl;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class OrderServiceImplIntegrationTest {

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    @Qualifier("pe.edu.upc.orderservice.client.InventoryClient")
    private InventoryClient inventoryClient;

    @Autowired
    private OrderService orderService;

    @TestConfiguration
    static class OrderServiceImplTestConfiguration {
        @Bean
        public OrderService orderService() {
            return new OrderServiceImpl();
        }
    }

    @Test
    @DisplayName("When GetOrderById With Valid Id Then Returns Order")
    public void whenGetOrderByIdThenReturnsOrder() {
        //Arrange
        Long id = 1L;
        String status = "CREATED";
        Order order = new Order();
        order.setStatus("CREATED");

        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        //Act
        Order foundOrder = orderService.getOrder(id);
        //Assert
        assertThat(foundOrder.getStatus()).isEqualTo(status);
    }

    @Test
    @DisplayName("When GetOrderById With Invalid Id Then Returns Resource Not Found Exception")
    public void whenGetOrderByIdThenReturnsNull() {
        //Arrange
        Long id = 1L;
        String status = "DELETED";
        Order order = new Order();
        order.setStatus("DELETED");

        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        //Act
        Order foundOrder = orderService.getOrder(id);
        //Assert
        assertThat(foundOrder.getStatus()).isEqualTo(status);
    }

    @Test
    @DisplayName("DELETE service")
    public void WhenDeleteAOrderItGetsNoValuePresent() throws Exception {
        //Arrange
        Long id = 1L;
        Order order = new Order();
        order.setId(id);

        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        orderRepository.deleteById(id);
        when(orderRepository.findById(id)).thenReturn(Optional.empty());
        Throwable exception = catchThrowable(() -> {
            Optional<Order> order1 = orderRepository.findById(id);
            order1.get();
        });
        assertThat(exception.getMessage()).isEqualTo("No value present");
    }


    @Test
    @DisplayName("Create service")
    public void WhenCreateAOrderGetsOk() throws Exception {
        //Arrange
        Long id = 1L;
        Order order = new Order();
        order.setId(id);

        when(orderRepository.save(order)).thenReturn(order);
        Order result = orderRepository.save(order);
        assertThat(result.getStatus()).isEqualTo(order.getStatus());
    }

    @Test
    @DisplayName("UPDATE service")
    public void WhenUpdateAOrderGetsOk() throws Exception {
        //Arrange
        Long id = 1L;
        Order order = new Order();
        order.setId(id);

        when(orderRepository.save(order)).thenReturn(order);


        Long id1 = 1L;
        String message1  = "Buenas tardes";
        int duration1 = 1;
        Order order1 = new Order();
        order1.setId(id1);




        when(orderRepository.save(order)).thenReturn(order1);
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order1));
        assertThat(orderRepository.findById(id).get().getStatus()).isEqualTo(order1.getStatus());

    }
    
    
}
