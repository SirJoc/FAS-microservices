package pe.edu.upc.locationservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.upc.locationservice.models.Coordinate;
import pe.edu.upc.locationservice.service.CoordinateService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Tag(name = "Coordinates", description = "Coordinate API")
@RestController
@RequestMapping("/coordinates")
public class CoordinateController {
    @Autowired
    private CoordinateService coordinateService;

    @GetMapping
    public ResponseEntity<List<Coordinate>> listCoordinate(@RequestParam(name="id", required = false) Long id){
        List<Coordinate> coordinates = new ArrayList<>();
        if (null==id){
            coordinates = coordinateService.listAllCoordinate();
            if (coordinates.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.ok(coordinates);
    }

    @PostMapping
    public ResponseEntity<Coordinate> createCoordinate(@RequestBody Coordinate coordinate, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Coordinate coordinateCreate =  coordinateService.createCoordinate(coordinate);
        return ResponseEntity.status(HttpStatus.CREATED).body(coordinateCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Coordinate> updateProduct(@PathVariable("id") Long id, @RequestBody Coordinate coordinate){
        coordinate.setId(id);
        Coordinate coordinateDB =  coordinateService.updateCoordinate(coordinate);
        if (coordinateDB == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(coordinateDB);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
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
}
