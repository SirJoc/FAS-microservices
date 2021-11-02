package pe.edu.upc.paymentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.edu.upc.paymentservice.model.User;

@FeignClient(name = "user-service")
@RequestMapping("/api")
public interface UserClient {
    @GetMapping("/users/{id}")
    public User fetchById(@PathVariable(name = "id") Long id);
}
