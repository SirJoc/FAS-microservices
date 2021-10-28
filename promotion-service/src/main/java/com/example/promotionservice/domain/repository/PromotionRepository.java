package com.example.promotionservice.domain.repository;

import com.example.promotionservice.domain.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    public Optional<Promotion> findById(Long promotionId);
}
