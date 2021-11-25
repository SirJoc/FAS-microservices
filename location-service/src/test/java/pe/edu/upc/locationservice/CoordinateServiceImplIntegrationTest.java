package pe.edu.upc.locationservice;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pe.edu.upc.locationservice.client.UserClient;
import pe.edu.upc.locationservice.entity.Coordinate;
import pe.edu.upc.locationservice.model.User;
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
    @MockBean
    @Qualifier("pe.edu.upc.paymentservice.client.UserClient")
    private UserClient userClient;
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
        User user = User.builder().fullName("none").id(coordinate.getUserId()).build();


        when(userClient.fetchById(coordinate.getUserId())).thenReturn(ResponseEntity.of(Optional.of(user)));
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
    @DisplayName("DELETE Coordinate")
    public void WhenDeleteACoordinateItGetsNoValuePresent() throws Exception {
        //Arrange
        Long id = 1L;
        String description = "Parque Kenedy";
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
        User user = User.builder().fullName("none").id(coordinate.getUserId()).build();
        when(userClient.fetchById(coordinate.getUserId())).thenReturn(ResponseEntity.of(Optional.of(user)));


        when(coordinateRepository.findById(id)).thenReturn(Optional.of(coordinate));
        coordinateService.deleteCoordinateById(id);
        when(coordinateRepository.findById(id)).thenReturn(null);
        Throwable exception = catchThrowable(() -> {
            Optional<Coordinate> coordinate1 = coordinateRepository.findById(id);
            coordinate1.get();
        });
        assertThat(exception.getMessage()).isEqualTo(null);
    }
    @Test
    @DisplayName("Create Coordinate")
    public void WhenCreateACoordinateGetsOk() throws Exception {
        //Arrange
        Long id = 1L;
        String description = "San Juan de Miraflores";
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


        when(coordinateRepository.save(coordinate)).thenReturn(coordinate);
        Coordinate result = coordinateService.createCoordinate(coordinate);
        assertThat(result.getStatus()).isEqualTo(coordinate.getStatus());
    }

    @Test
    @DisplayName("Update Coordinate")
    public void WhenUpdateACoordinateGetsOk() throws Exception {
        //Arrange
        Long id = 1L;
        String description = "San Juan de Lurigancho";
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


        when(coordinateRepository.save(coordinate)).thenReturn(coordinate);
        Long id1 = 1L;
        String description1 = "San Juan de Viva Cristo Rey";
        double longitude1 = 5.124213;
        double latitude1 = 2.421213;
        double altitude1 = 1.12344;
        boolean favourite_route1 = true;
        String status1 = "ENABLE";
        Coordinate coordinate1 = new Coordinate();

        coordinate1.setId(id);
        coordinate1.setAltitude((float) altitude);
        coordinate1.setDescription(description);
        coordinate1.setFavorite_route(favourite_route);
        coordinate1.setLongitude((float) longitude);
        coordinate1.setLatitude((float) altitude);
        coordinate1.setStatus(status);
        coordinate1.setUserId(1L);
        User user = User.builder().fullName("none").id(coordinate.getUserId()).build();
        when(userClient.fetchById(coordinate.getUserId())).thenReturn(ResponseEntity.of(Optional.of(user)));

        when(coordinateRepository.save(coordinate)).thenReturn(coordinate1);
        Coordinate result = coordinateService.updateCoordinate(coordinate);
        when(coordinateRepository.findById(coordinate.getId())).thenReturn(Optional.of(coordinate1));
        assertThat(coordinateService.getCoordinateById(id).getStatus()).isEqualTo(coordinate1.getStatus());
    }

}
