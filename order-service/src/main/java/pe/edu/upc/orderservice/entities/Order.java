package pe.edu.upc.orderservice.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO: add attribute => date: Datetime

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @Column(length = 1, nullable = false)
    private String status;

    // Relationships
    @OneToOne(mappedBy = "order")
    private OrderDetail orderDetail;
}
