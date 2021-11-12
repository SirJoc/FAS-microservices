package pe.edu.upc.reviewservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.edu.upc.reviewservice.model.User;

@FeignClient(name = "user-service", fallback = UserHystrixFallbackFactory.class)
public interface UserClient {
    @GetMapping(value = "api/users/{id}")
    ResponseEntity<User> getUser(@PathVariable("id") long id);
}
