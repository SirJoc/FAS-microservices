package pe.edu.upc.reviewservice.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pe.edu.upc.reviewservice.model.User;

@Component
public class UserHystrixFallbackFactory implements UserClient{
    @Override
    public ResponseEntity<User> getUser(long id) {
        User user = User.builder()
                .fullName("none")
                .password("none")
                .email("none")
                .ruc("none")
                .createAt(null)
                .status("none").build();
        return ResponseEntity.ok(user);
    }
}
