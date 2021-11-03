package pe.edu.upc.paymentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.paymentservice.model.User;

import javax.validation.Valid;

@FeignClient(name = "user-service")
@RequestMapping("/api")
public interface UserClient {
    @GetMapping("/users/{id}")
    public User fetchById(@PathVariable(name = "id") Long id);
    @PutMapping("/users/{id}")
    public User updateUser(@Valid @RequestBody User user, @PathVariable(name = "id") Long id);

}
