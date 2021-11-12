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
import pe.edu.upc.orderservice.entities.OrderDetail;
import pe.edu.upc.orderservice.services.OrderDetailService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrdersDetailsController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Operation(summary = "Get OrderDetail by OrderId", description = "Get an OrderDetail by OrderId", tags = {"ordersDetails"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OrderDetail returned", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{orderId}/order-detail")
    public ResponseEntity<OrderDetail> getOrderDetailByOrderId(@PathVariable("orderId") Long orderId) {
        OrderDetail orderDetail =  orderDetailService.getOrderDetailByOrderId(orderId);
        if (null==orderDetail){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderDetail);
    }

    @Operation(summary = "Add OrderDetail", description = "Create new orderDetail", tags = {"ordersDetails"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order Detail created", content = @Content(mediaType = "application/json")),
    })
    @PostMapping("/{orderId}")
    public ResponseEntity<OrderDetail> createOrderDetail(@PathVariable("orderId") Long orderId, @Valid @RequestBody OrderDetail orderDetail, BindingResult result) {

        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        OrderDetail orderDetailCreate =  orderDetailService.createOrderDetail(orderId, orderDetail);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDetailCreate);
    }

    @Operation(summary = "Update OrderDetail", description = "Update orderDetail by OrderId", tags = {"ordersDetails"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order Detail Updated", content = @Content(mediaType = "application/json")),
    })
    @PutMapping("/{orderId}/order-detail/{orderDetailId}")
    public ResponseEntity<OrderDetail> updateOrderDetail(@PathVariable("orderId") Long orderId, @PathVariable("orderDetailId") Long orderDetailId, @RequestBody OrderDetail orderDetail) {
        orderDetail.setId(orderDetailId);
        OrderDetail orderDetailDB =  orderDetailService.updateOrderDetail(orderId, orderDetail);
        if (orderDetailDB == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderDetailDB);
    }

    @Operation(summary = "Delete OrderDetail", description = "Deleted orderDetail by OrderId", tags = {"ordersDetails"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order Detail deleted", content = @Content(mediaType = "application/json")),
    })
    @DeleteMapping("/{orderId}/order-detail/{orderDetailId}")
    public ResponseEntity<OrderDetail> deleteSessionDetail(@PathVariable("orderId") Long orderId, @PathVariable("orderDetailId") Long orderDetailId) {

        OrderDetail orderDetailDelete = orderDetailService.deleteOrderDetail(orderId, orderDetailId);
        if (orderDetailDelete == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderDetailDelete);
    }

    /* Format message */
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
