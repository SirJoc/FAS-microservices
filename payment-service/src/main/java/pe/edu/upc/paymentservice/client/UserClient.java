package pe.edu.upc.paymentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.paymentservice.model.User;

@FeignClient(name = "user-service", fallback = UserHystrixFallbackFactory.class)
public interface UserClient {
    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> fetchById(@PathVariable(name = "id") Long id);
    @PutMapping("/api/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody ResponseEntity<User> user, @PathVariable(name = "id") Long id);

}
