package pe.edu.upc.usersservice.model;

import lombok.Data;
import pe.edu.upc.usersservice.entity.User;


@Data
public class Publicity {
    private Long id;
    private String message;
    private int duration;
    private Long userId;
}
