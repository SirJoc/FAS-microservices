package pe.edu.upc.orderservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.upc.orderservice.entities.Order;
import pe.edu.upc.orderservice.services.OrderService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @Operation(summary = "Get all orders", description = "Get all orders", tags = {"orders"})
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "All orders returned", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Orders not found")
    })
    public ResponseEntity<List<Order>> listOrder() {
        List<Order> orders = orderService.listAllOrders();

        if (orders.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Get Order", description = "Get Order by orderId", tags = {"orders"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order returned", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable("id") Long id) {
        Order order =  orderService.getOrder(id);
        if (null==order){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "Add Order", description = "Create new order", tags = {"orders"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created", content = @Content(mediaType = "application/json")),
    })
    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Order orderCreate =  orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderCreate);
    }

    @Operation(summary = "Update Order", description = "Update order by orderId", tags = {"orders"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order Updated", content = @Content(mediaType = "application/json")),
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody Order order){
        order.setId(id);
        Order orderDB =  orderService.updateOrder(order);
        if (orderDB == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderDB);
    }

    @Operation(summary = "Delete Order", description = "Deleted order by orderId", tags = {"orders"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order deleted", content = @Content(mediaType = "application/json")),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable("id") Long id){
        Order orderDelete = orderService.deleteOrder(id);
        if (orderDelete == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderDelete);
    }

    private String formatMessage(BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
