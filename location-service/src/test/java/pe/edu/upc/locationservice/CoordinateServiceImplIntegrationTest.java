package pe.edu.upc.locationservice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pe.edu.upc.locationservice.entity.Coordinate;
import pe.edu.upc.locationservice.repository.CoordinateRepository;
import pe.edu.upc.locationservice.service.CoordinateService;
import pe.edu.upc.locationservice.service.Impls.CoordinateServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(SpringExtension.class)
public class CoordinateServiceImplIntegrationTest {
    @MockBean
    private CoordinateRepository coordinateRepository;
    @Autowired
    private CoordinateService coordinateService;

    @TestConfiguration
    static class PublicityImplTestConfiguration{
        @Bean
        public CoordinateService publicityService() {
            return new CoordinateServiceImpl();
        }
    }

    @Test
    @DisplayName("When GetCoordinateById With Valid Id Then Returns Coordinate")
    public void whenGetCoordinateByIdWithValidIdThenReturnsCoordinate() throws Exception {
        //Arrange
        Long id = 1L;
        String description = "Entrega en casa";
        double longitude = 5.124213;
        double latitude = 2.421213;
        double altitude = 1.12344;
        boolean favourite_route = true;
        String status = "ENABLE";



        Coordinate coordinate = new Coordinate();
        coordinate.setId(id);
        coordinate.setAltitude((float) altitude);
        coordinate.setDescription(description);
        coordinate.setFavorite_route(favourite_route);
        coordinate.setLongitude((float) longitude);
        coordinate.setLatitude((float) altitude);
        coordinate.setStatus(status);


        coordinate.setUserId(1L);
        when(coordinateRepository.findById(id)).thenReturn(Optional.of(coordinate));


        //Act
        Coordinate foundCoordinate = coordinateService.getCoordinateById(id);

        //Assert
        assertThat(foundCoordinate.getStatus()).isEqualTo(status);
    }

    @Test
    @DisplayName("When GetCoordinateById With Invalid Id Then Returns Coordinate")
    public void whenGetCoordinateByIdWithInvalidIdThenReturnsCoordinate() throws Exception {
        //Arrange
        Long id = 1L;

        Coordinate coordinate = new Coordinate();
        coordinate.setId(id);

        when(coordinateRepository.findById(id)).thenReturn(Optional.empty());

        //Act
        Throwable exception = catchThrowable(() -> {
            Coordinate coordinate1 = coordinateService.getCoordinateById(id);
        });

        //Assert
        assertThat(exception).isEqualTo(null);

    }

    @Test
    @DisplayName("When PostCoordinate With Valid Values Then Create Coordinate")
    public void WhenPostCoordinateWithValidValuesThenCreateCoordinate() throws Exception {

        Long id = 1L;
        String description = "Envio a Edificio";
        double longitude = 9.134213;
        double latitude = 4.421213;
        double altitude = 8.12344;
        boolean favourite_route = true;
        String status = "ENABLE";

        Coordinate coordinate = new Coordinate();
        coordinate.setId(id);
        coordinate.setAltitude((float) altitude);
        coordinate.setDescription(description);
        coordinate.setFavorite_route(favourite_route);
        coordinate.setLongitude((float) longitude);
        coordinate.setLatitude((float) altitude);
        coordinate.setStatus(status);

        when(coordinateRepository.save(coordinate)).thenReturn(coordinate);

        //Act
        Coordinate create0Coordinate = coordinateService.createCoordinate(coordinate);

        //Assert
        assertThat(create0Coordinate.getStatus()).isEqualTo(status);
    }
}
