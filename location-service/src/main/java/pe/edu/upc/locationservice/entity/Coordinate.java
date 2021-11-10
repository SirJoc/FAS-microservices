package pe.edu.upc.locationservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.locationservice.model.User;

import javax.persistence.*;

@Entity
@Table(name = "coordinates")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Coordinate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40, nullable = false)
    private String description;

    @Column(length = 40, nullable = false)
    private Float longitude;

    @Column(length = 40, nullable = false)
    private Float latitude;

    @Column(length = 40, nullable = false)
    private Float altitude;

    @Column(length = 40, nullable = false)
    private Boolean favorite_route;

    private String status;

    @Column(name = "user_id")
    private Long userId;

    @Transient
    private User user;
}
