package pe.edu.upc.usersservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.usersservice.model.Subscription;

@FeignClient(name = "subscription-service")
@RequestMapping("/api")
public interface SubscriptionClient {
    @PostMapping("/users/{id}/subscriptions")
    public ResponseEntity<Subscription> createSubscription(@PathVariable("id") long userId, @RequestBody Subscription subscription, BindingResult result);

    @GetMapping(value = "/subscriptions/id/{id}")
    public ResponseEntity<Subscription> fetchById(@PathVariable("id") Long subscriptionId);
}