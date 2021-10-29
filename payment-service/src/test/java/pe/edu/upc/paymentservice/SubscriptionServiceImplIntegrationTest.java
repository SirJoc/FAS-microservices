package pe.edu.upc.paymentservice;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pe.edu.upc.paymentservice.entities.Publicity;
import pe.edu.upc.paymentservice.entities.Subscription;
import pe.edu.upc.paymentservice.repository.SubscriptionRepository;
import pe.edu.upc.paymentservice.service.SubscriptionService;
import pe.edu.upc.paymentservice.service.impl.SubscriptionServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(SpringExtension.class)
public class SubscriptionServiceImplIntegrationTest {
    @MockBean
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @TestConfiguration
    static class SubscriptionImplTestConfiguration{
        @Bean
        public SubscriptionService publicityService() {
            return new SubscriptionServiceImpl();
        }
    }

    @Test
    @DisplayName("When GetSubscriptionById With Valid Id Then Returns Subscription")
    public void WhenGetSubscriptionByIdWithValidIdThenReturnsSubscription() throws Exception {
        // Arrange
        Long id = 1L;
        String type = "Premium";

        Subscription subscription = new Subscription();
        subscription.setId(id);
        subscription.setType(type);

        when(subscriptionRepository.findById(id)).thenReturn(java.util.Optional.of(subscription));

        // Act
        Optional<Subscription> foundSubscription = subscriptionService.findById(id);


        assertThat(foundSubscription.get()).isEqualTo(subscription);
    }

    @Test
    @DisplayName("When GetSubscriptionById With Invalid Id Then Returns Subscription")
    public void WhenGetSubscriptionByIdWithInvalidIdThenReturnsSubscription() throws Exception {
        // Arrange
        Long id = 1L;
        String type = "Premium";

        Subscription subscription = new Subscription();
        subscription.setId(id);
        subscription.setType(type);

        when(subscriptionRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Throwable exception = catchThrowable(() -> {
            Optional<Subscription> subscription1 = subscriptionService.findById(id);
            subscription1.get();
        });


        assertThat(exception.getMessage()).isEqualTo("No value present");
    }

}
