package pe.edu.upc.orderservice.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
@Data
@Builder
public class Product {
    private Long id;
    private String name;
    private String description;
    private String corporation;
    private Double price;
    private Long userId;
    private Long orderId;
}
