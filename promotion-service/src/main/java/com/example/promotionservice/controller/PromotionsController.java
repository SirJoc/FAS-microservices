package com.example.promotionservice.controller;

import com.example.promotionservice.domain.model.Promotion;
import com.example.promotionservice.domain.service.PromotionService;
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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/promotions")
public class PromotionsController {
   @Autowired
   private PromotionService promotionService;

   @GetMapping
   @Operation(summary = "Get all promotions", description = "Get all promotions", tags = {"promotions"})
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "All promotions returned", content = @Content(mediaType = "application/json")),
           @ApiResponse(responseCode = "404", description = "Promotions not founded")
   })
   public ResponseEntity<List<Promotion>> listPromotion(){
       List<Promotion> promotions = new ArrayList<>();
       promotions = promotionService.getAllPromotions();
       if(promotions.isEmpty()){
           return ResponseEntity.noContent().build();
       }
       return ResponseEntity.ok(promotions);
   }

   @GetMapping(value = "/{id}")
   @Operation(summary = "Get Promotion", description = "Get Promotion by promotionId", tags = {"promotions"})
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Promotion returned", content = @Content(mediaType = "application/json")),
           @ApiResponse(responseCode = "404", description = "Promotion not found")
   })
   public ResponseEntity<Promotion> getPromotion(@PathVariable("id") Long id){
       Promotion promotion = promotionService.getPromotionById(id);
       if(null == promotion){
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(promotion);
   }

   @DeleteMapping(value = "/{id}")
   @Operation(summary = "Delete Promotion", description = "Deleted Promotion by promotionId", tags = {"promotions"})
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Promotion deleted", content = @Content(mediaType = "application/json")),
   })
   public ResponseEntity<Promotion> deleteProduct(@PathVariable("id") Long id){
       Promotion promotionDelete = promotionService.deletePromotion(id);
       if(promotionDelete == null){
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(promotionDelete);
   }

   @PostMapping
   @Operation(summary = "Add Promotion", description = "Create new promotion", tags = {"promotions"})
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Promotion created", content = @Content(mediaType = "application/json")),
   })
   public ResponseEntity<Promotion> createPromotion(@Valid @RequestBody Promotion promotion, BindingResult result){
       if (result.hasErrors()){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
       }
       Promotion promotionCreate =  promotionService.createPromotion(promotion);
       return ResponseEntity.status(HttpStatus.CREATED).body(promotionCreate);
   }


   @PutMapping(value = "/{id}")
   @Operation(summary = "Update Promotion", description = "Update promotion by promotionId", tags = {"promotions"})
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Promotion Updated", content = @Content(mediaType = "application/json")),
   })
   public ResponseEntity<Promotion> updatePromotion(@PathVariable("id") Long id, @RequestBody Promotion promotion){
       promotion.setId(id);
       Promotion promotionDB =  promotionService.updatePromotion(promotion);
       if (promotionDB == null){
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(promotionDB);
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
