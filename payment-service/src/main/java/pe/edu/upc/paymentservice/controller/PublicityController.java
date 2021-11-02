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
import pe.edu.upc.paymentservice.entities.Publicity;
import pe.edu.upc.paymentservice.service.PublicityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PublicityController {
    @Autowired
    PublicityService publicityService;

    @GetMapping(value = "/publicities")
    @Operation(summary = "Get all publicities", description = "Get all publicities", tags = {"publicities"})
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "All publicities returned", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Publicities not found")
    })
    public ResponseEntity<List<Publicity>> listAllPublicity() throws Exception {
        List<Publicity> publicities = publicityService.findAll();
        System.out.println(publicities);
        if (publicities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(publicities);
    }

    @Operation(summary = "Add Publicity", description = "Create new Publicity", tags = {"publicities"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicity created", content = @Content(mediaType = "application/json")),
    })
    @PostMapping("/user/{id}/publicities")
    public ResponseEntity<Publicity> createPublicity(@PathVariable("id") long userId, @RequestBody Publicity publicity, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Publicity publicityDB = publicityService.save(userId, publicity);
        return ResponseEntity.status(HttpStatus.CREATED).body(publicityDB);
    }


    @Operation(summary = "Update publicity", description = "Update publicity by publicityId", tags = {"publicities"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "publicity Updated", content = @Content(mediaType = "application/json")),
    })
    @PutMapping(value = "/publicities/{id}")
    public ResponseEntity<Publicity> updatePublicity(@PathVariable("id") long id, @RequestBody Publicity entity) {
        try {
            Optional<Publicity> publicity = publicityService.findById(id);
            if (publicity.isPresent()) {
                publicity = convertToEntity(entity);
                return ResponseEntity.ok(publicity.get());
            }else {
                return ResponseEntity.notFound().build();
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @Operation(summary = "Get Publicity", description = "Get Publicity by publicityId", tags = {"publicities"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicity returned", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Publicity not found")
    })
    @GetMapping(value = "/publicities/id/{id}")
    public ResponseEntity<Publicity> fetchById(@PathVariable("id") Long id) {
        try {
            Optional<Publicity> optionalPublicity = publicityService.findById(id);
            if (optionalPublicity.isPresent()){
                return new ResponseEntity<Publicity>(optionalPublicity.get(), HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete Publicity", description = "Deleted Publicity by publicityId", tags = {"publicities"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicity deleted", content = @Content(mediaType = "application/json")),
    })
    @DeleteMapping(value = "/publicities/id/{id}")
    public void deleteById(@PathVariable("id") Long id) throws Exception {
        publicityService.deleteById(id);
    }

    @RequestMapping(value = "/publicities/users/{id}")
    public List<Publicity> getPublicitiesByUserId(@PathVariable("id") Long userId) throws Exception {
        return publicityService.getAllPublicitiesByUserId(userId);
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

    private Optional<Publicity> convertToEntity(Publicity resource) {
        Optional<Publicity> publicity = Optional.of(new Publicity());
        publicity.get().setId(resource.getId());
        publicity.get().setDuration(resource.getDuration());
        publicity.get().setMessage(resource.getMessage());
        return publicity;
    }
}
