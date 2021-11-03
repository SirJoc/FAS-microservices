package pe.edu.upc.paymentservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upc.paymentservice.model.User;

import javax.persistence.*;

@Entity
@Table(name = "publicities")
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Publicity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40, nullable = false)
    private String message;

    @Column(length = 40, nullable = false)
    private int duration;

    @Column(name = "user_id")
    private Long userId;
}
