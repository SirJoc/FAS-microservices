package com.example.promotionservice.service;

import com.example.promotionservice.domain.model.Promotion;
import com.example.promotionservice.domain.repository.PromotionRepository;
import com.example.promotionservice.domain.service.PromotionService;
import com.example.promotionservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    @Override
    public Promotion getPromotionById(Long promotionId) {
        return promotionRepository.findById(promotionId)
                .orElseThrow(()->new ResourceNotFoundException("Promotion", "id", promotionId));
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    @Override
    public Promotion createPromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    @Override
    public Promotion updatePromotion(Promotion promotion) {
        Promotion promotionDB = getPromotionById(promotion.getId());
        promotionDB.setType(promotion.getType());
        promotionDB.setStartDate(promotion.getStartDate());
        promotionDB.setEndDate(promotion.getEndDate());

        return promotionRepository.save(promotionDB);
    }

    @Override
    public Promotion deletePromotion(Long id) {
        Promotion promotionDB = getPromotionById(id);
        return promotionRepository.save(promotionDB);
    }
}
