package upc.edu.pe.inventoryservice.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import upc.edu.pe.inventoryservice.model.User;

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
