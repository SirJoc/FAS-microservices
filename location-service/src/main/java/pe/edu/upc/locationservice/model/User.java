package pe.edu.upc.locationservice.model;

import javax.persistence.*;
import java.util.Date;

public class User {
    private Long id;
    private String fullName;
    private String password;
    private String email;
    private String ruc;
    private Date createAt;
    private String status;
}
