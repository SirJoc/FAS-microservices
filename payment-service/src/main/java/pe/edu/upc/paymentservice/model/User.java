package pe.edu.upc.paymentservice.model;

import lombok.Data;
import pe.edu.upc.paymentservice.entities.Publicity;
import pe.edu.upc.paymentservice.entities.Subscription;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
public class User {
    private Long id;
    private String fullName;
    private String password;
    private String email;
    private String ruc;
    private Date createAt;
    private String status;
    private List<Publicity> publicities;
}
