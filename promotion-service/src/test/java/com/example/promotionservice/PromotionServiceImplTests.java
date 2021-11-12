package com.example.promotionservice;

import com.example.promotionservice.domain.model.Promotion;
import com.example.promotionservice.domain.repository.PromotionRepository;
import com.example.promotionservice.service.PromotionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void findAllPromotions() throws Exception{
        Promotion promotion = new Promotion();
        promotion.setId(1L);
        promotion.setStartDate("12/10/2020");
        promotion.setEndDate("15/10/2020");
        promotion.setType("uno");
        Promotion promotion1 = new Promotion();
        promotion.setId(2L);
        promotion.setStartDate("13/10/2020");
        promotion.setEndDate("14/10/2020");
        promotion.setType("dos");
        Promotion promotion2 = new Promotion();
        promotion.setId(3L);
        promotion.setStartDate("14/10/2020");
        promotion.setEndDate("16/10/2020");
        promotion.setType("tres");
        List<Promotion> promotionList = new ArrayList<>();
        promotionList.add(promotion);
        promotionList.add(promotion1);
        promotionList.add(promotion2);
        given(promotionRepository.findAll()).willReturn(promotionList);
        List<Promotion> expected = promotionService.getAllPromotions();
        assertEquals(expected, promotionList);
    }

    @Test
    void deletePromotion() throws Exception{
        Promotion promotion = new Promotion();
        promotion.setId(1L);
        promotion.setStartDate("12/10/2020");
        promotion.setEndDate("15/10/2020");
        promotion.setType("uno");

        when(promotionRepository.findById(1L)).thenReturn(Optional.of(promotion));
        promotionService.deletePromotion(1L);
        when(promotionRepository.findById(1L)).thenReturn(Optional.empty());
        Throwable exception = catchThrowable(() -> {
            Promotion promotion1 = promotionService.getPromotionById(1L);
        });
        assertThat(exception.getMessage()).isEqualTo("Resource Promotion not found for id with value 1");
    }
}

