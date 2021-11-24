package pe.edu.upc.paymentservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upc.paymentservice.model.User;

import javax.persistence.*;

@Entity
@Table(name = "subscriptions")
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40, nullable = false)
    private String type;

    @Column(name = "user_id")
    private Long userId;

    @Transient
    private User user;
}
