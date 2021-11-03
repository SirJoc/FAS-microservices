package pe.edu.upc.usersservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.usersservice.model.Publicity;

import java.util.List;

@FeignClient(name = "payment-service")
@RequestMapping("/api/publicities")
public interface PublicityClient {
    @PostMapping("/user/{id}/publicities")
    public ResponseEntity<Publicity> createPublicity(@PathVariable("id") long userId, @RequestBody Publicity publicity, BindingResult result) throws Exception;
    @GetMapping(value = "/id/{id}")
    public ResponseEntity<Publicity> fetchById(@PathVariable("id") Long id) throws Exception;
    @RequestMapping(value = "/publicities/users/{id}")
    public List<Publicity> getPublicitiesByUserId(@PathVariable("id") Long userId);

}
