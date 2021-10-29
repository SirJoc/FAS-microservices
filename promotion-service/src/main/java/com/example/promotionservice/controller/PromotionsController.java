package com.example.promotionservice.controller;

import com.example.promotionservice.domain.model.Promotion;
import com.example.promotionservice.domain.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/promotions")
public class PromotionsController {
   @Autowired
   private PromotionService promotionService;

   @GetMapping
   public ResponseEntity<List<Promotion>> listPromotion(){
       List<Promotion> promotions = new ArrayList<>();
       promotions = promotionService.getAllPromotions();
       if(promotions.isEmpty()){
           return ResponseEntity.noContent().build();
       }
       return ResponseEntity.ok(promotions);
   }

   @GetMapping(value = "/{id}")
   public ResponseEntity<Promotion> getPromotion(@PathVariable("id") Long id){
       Promotion promotion = promotionService.getPromotionById(id);
       if(null == promotion){
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(promotion);
   }
}
