package pe.edu.upc.paymentservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.upc.paymentservice.entities.Subscription;
import pe.edu.upc.paymentservice.service.SubscriptionService;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class SubscriptionController {
    @Autowired
    SubscriptionService subscriptionService;

    @GetMapping("/subscriptions")
    @Operation(summary = "Get all subscriptions", description = "Get all subscriptions", tags = {"subscriptions"})
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "All subscriptions returned", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Subscriptions not found")
    })
    public ResponseEntity<List<Subscription>> listAllSubscription() throws Exception {
        List<Subscription> subscriptions = subscriptionService.findAll();
        System.out.println(subscriptions);
        if (subscriptions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subscriptions);
    }

    @Operation(summary = "Add Subscription", description = "Create new subscription", tags = {"subscriptions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription created", content = @Content(mediaType = "application/json")),
    })
    @PostMapping("/users/{id}/subscriptions")
    public ResponseEntity<Subscription> createSubscription(@PathVariable("id") long userId, @RequestBody Subscription subscription, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Subscription subscriptionDB = subscriptionService.save(subscription);
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionDB);
    }


    @Operation(summary = "Update Subscription", description = "Update subscription by subscriptionId", tags = {"subscriptions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription Updated", content = @Content(mediaType = "application/json")),
    })
    @PutMapping(value = "/subscriptions/{id}")
    public ResponseEntity<Subscription> updateSubscription(@PathVariable("id") long id, @RequestBody Subscription entity) {
        try {
            Optional<Subscription> subscription = subscriptionService.findById(id);
            if (subscription.isPresent()) {
                subscription = convertToEntity(entity);
                return ResponseEntity.ok(subscription.get());
            }else {
                return ResponseEntity.notFound().build();
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Operation(summary = "Get Subscription", description = "Get Subscription by subscriptionId", tags = {"subscriptions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription returned", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Subscription not found")
    })
    @GetMapping(value = "/subscriptions/id/{id}")
    public ResponseEntity<Subscription> fetchById(@PathVariable("id") Long id) {
        try {
            Optional<Subscription> optionalSubscription = subscriptionService.findById(id);
            if (optionalSubscription.isPresent()){
                return new ResponseEntity<Subscription>(optionalSubscription.get(), HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete Subscription", description = "Deleted subscription by subscriptionId", tags = {"subscriptions"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription deleted", content = @Content(mediaType = "application/json")),
    })
    @DeleteMapping(value = "/subscriptions/id/{id}")
    public void deleteById(@PathVariable("id") Long id) throws Exception {
        subscriptionService.deleteById(id);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    private Optional<Subscription> convertToEntity(Subscription resource) {
        Optional<Subscription> subscription = Optional.of(new Subscription());
        subscription.get().setId(resource.getId());
        subscription.get().setType(resource.getType());
        return subscription;
    }
}
