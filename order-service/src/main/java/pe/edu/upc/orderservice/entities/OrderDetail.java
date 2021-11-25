package pe.edu.upc.orderservice.entities;

import lombok.Data;
import pe.edu.upc.orderservice.model.Product;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders_details")
@Data
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer price;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @Column(length = 10, nullable = false)
    private String status;

    @Transient
    private List<Product> products;

    // Relationships
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "order_id")
    private Order order;
}
