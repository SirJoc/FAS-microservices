package pe.edu.upc.locationservice.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pe.edu.upc.locationservice.model.User;

@Component
public class UserHystrixFallbackFactory implements UserClient{
    @Override
    public ResponseEntity<User> fetchById(Long id) {
        User user = User.builder()
                .fullName("none")
                .email("none")
                .ruc("none").build();
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<User> updateUser(ResponseEntity<User> user, Long id) {
        User user1 = User.builder()
                .fullName("none")
                .email("none")
                .ruc("none").build();
        return ResponseEntity.ok(user1);
    }
}
