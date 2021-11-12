package pe.edu.upc.reviewservice.model;

import lombok.Builder;
import lombok.Data;
import java.util.Date;

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
