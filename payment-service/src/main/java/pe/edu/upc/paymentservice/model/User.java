package pe.edu.upc.paymentservice.model;

import lombok.Builder;
import lombok.Data;
import pe.edu.upc.paymentservice.entities.Publicity;
import pe.edu.upc.paymentservice.entities.Subscription;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class User {
    private Long id;
    private String fullName;
    private String password;
    private String email;
    private String ruc;
    private Date createAt;
    private String status;
}
