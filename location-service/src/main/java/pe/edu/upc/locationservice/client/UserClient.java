package pe.edu.upc.locationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pe.edu.upc.locationservice.model.User;

@FeignClient(name = "user-service", fallback = UserHystrixFallbackFactory.class)
public interface UserClient {
    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> fetchById(@PathVariable(name = "id") Long id);
    @PutMapping("/api/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody ResponseEntity<User> user, @PathVariable(name = "id") Long id);

}
