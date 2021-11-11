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
import pe.edu.upc.paymentservice.repository.PublicityRepository;
import pe.edu.upc.paymentservice.service.PublicityService;
import pe.edu.upc.paymentservice.service.impl.PublicityServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(SpringExtension.class)
public class PublicityServiceImplIntegrationTest {
    @MockBean
    private PublicityRepository publicityRepository;

    @MockBean
    private PublicityService publicityService;

    @TestConfiguration
    static class PublicityImplTestConfiguration{
        @Bean
        public PublicityService publicityService() {
            return new PublicityServiceImpl();
        }
    }

    @Test
    @DisplayName("When GetPublicityById With Valid Id Then Returns Publicity")
    public void whenGetPublicityByIdWithValidIdThenReturnsPublicity() throws Exception {
        //Arrange
        Long id = 1L;
        String message  = "Nuevos días";
        int duration = 1;

        Publicity publicity = new Publicity();
        publicity.setId(id);
        publicity.setMessage(message);
        publicity.setDuration(duration);

        when(publicityService.findById(id)).thenReturn(Optional.of(publicity));

        //Act
        Optional<Publicity> foundPublicity = publicityService.findById(id);

        //Assert
        assertThat(foundPublicity.get().getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("When GetPublicityById With Invalid Id Then Returns Publicity")
    public void whenGetPublicityByIdWithInvalidIdThenReturnsPublicity() throws Exception {
        //Arrange
        Long id = 1L;
        String message  = "Nuevos días";
        int duration = 1;

        Publicity publicity = new Publicity();
        publicity.setId(id);
        publicity.setMessage(message);
        publicity.setDuration(duration);

        when(publicityService.findById(id)).thenReturn(Optional.empty());

        //Act
        Throwable exception = catchThrowable(() -> {
            Optional<Publicity> publicity1 = publicityService.findById(id);
            publicity1.get();
        });

        //Assert
        assertThat(exception.getMessage()).isEqualTo("No value present");

    }

    @Test
    @DisplayName("DELETE service")
    public void WhenDeleteASubscriptionItGetsNoValuePresent() throws Exception {
        //Arrange
        Long id = 1L;
        String message  = "Nuevos días";
        int duration = 1;
        Publicity publicity = new Publicity();
        publicity.setId(id);
        publicity.setMessage(message);
        publicity.setDuration(duration);


        when(publicityService.findById(id)).thenReturn(Optional.of(publicity));
        publicityService.deleteById(id);
        when(publicityService.findById(id)).thenReturn(Optional.empty());
        Throwable exception = catchThrowable(() -> {
            Optional<Publicity> publicity1 = publicityService.findById(id);
            publicity1.get();
        });
        assertThat(exception.getMessage()).isEqualTo("No value present");
    }


    @Test
    @DisplayName("Create service")
    public void WhenCreateASubscriptionGetsOk() throws Exception {
        //Arrange
        Long id = 1L;
        String message  = "Nuevos días";
        int duration = 1;
        Publicity publicity = new Publicity();
        publicity.setId(id);
        publicity.setMessage(message);
        publicity.setDuration(duration);

        when(publicityService.save(publicity)).thenReturn(publicity);
        Publicity result = publicityService.save(publicity);
        assertThat(result.getMessage()).isEqualTo(publicity.getMessage());
    }


    @Test
    @DisplayName("UPDATE service")
    public void WhenUpdateASubcriptionGetsOk() throws Exception {
        //Arrange
        Long id = 1L;
        String message  = "Nuevos días";
        int duration = 1;
        Publicity publicity = new Publicity();
        publicity.setId(id);
        publicity.setMessage(message);
        publicity.setDuration(duration);

        when(publicityService.save(publicity)).thenReturn(publicity);


        Long id1 = 1L;
        String message1  = "Buenas tardes";
        int duration1 = 1;
        Publicity publicity1 = new Publicity();
        publicity1.setId(id);
        publicity1.setMessage(message);
        publicity1.setDuration(duration);




        when(publicityService.save(publicity)).thenReturn(publicity1);
        when(publicityService.findById(publicity.getId())).thenReturn(Optional.of(publicity1));
        assertThat(publicityService.findById(id).get().getMessage()).isEqualTo(publicity1.getMessage());

    }
}
