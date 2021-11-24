package com.example.promotionservice.domain.service;

import com.example.promotionservice.domain.model.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PromotionService {
    public Promotion getPromotionById(Long promotionId);
    public List<Promotion> getAllPromotions();
    public Promotion createPromotion(Promotion promotion);
    public Promotion updatePromotion(Promotion promotion);
    public void deletePromotion(Long id);
}
