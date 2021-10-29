package com.example.promotionservice;

import com.example.promotionservice.domain.model.Promotion;
import com.example.promotionservice.domain.repository.PromotionRepository;
import com.example.promotionservice.service.PromotionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class PromotionServiceImplTests {
    @Mock
    private PromotionRepository promotionRepository;

    @InjectMocks
    private PromotionServiceImpl promotionService;

    @Test
    public void savePromotion(){
        Promotion promotion = new Promotion();
        promotion.setId(1L);
        promotion.setStartDate("12/10/2020");
        promotion.setEndDate("15/10/2020");
        promotion.setType("dos");
        given(promotionRepository.save(promotion)).willReturn(promotion);
        Promotion savedPromotion = null;
        savedPromotion = promotionService.createPromotion(promotion);
        assertThat(savedPromotion).isNotNull();
        verify(promotionRepository).save(any(Promotion.class));
    }

    @Test
    void findByIdTest() throws Exception {
        Long id = 1L;
        Promotion promotion = new Promotion();
        promotion.setId(1L);
        promotion.setStartDate("12/10/2020");
        promotion.setEndDate("15/10/2020");
        promotion.setType("dos");
        given(promotionRepository.findById(id)).willReturn(Optional.of(promotion));
        Promotion expected = promotionService.getPromotionById(id);
        assertThat(expected).isNotNull();
    }
}

