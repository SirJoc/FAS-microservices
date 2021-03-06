package pe.edu.upc.usersservice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pe.edu.upc.usersservice.entity.User;
import pe.edu.upc.usersservice.exceptions.ResourceNotFoundException;
import pe.edu.upc.usersservice.repository.UserRepository;
import pe.edu.upc.usersservice.service.UserService;
import pe.edu.upc.usersservice.service.impls.UserServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceImplIntegrationTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @TestConfiguration
    static class UserImplTestConfiguration {
        @Bean
        public UserService userServiceService(){
            return new UserServiceImpl();
        }
    }

    @Test
    @DisplayName("When GetUserById With Valid Id Then Returns User Profile")
    public void whenGetUserByIdThenReturnsUser(){
        //Arrange
        Long id = 1L;
        String name = "Prueba1";
        User user = new User();
        user.setId(1L);
        user.setFullName(name);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        //Act
        User foundUser = userService.findById(id);
        //Assert
        assertThat(foundUser.getFullName()).isEqualTo(name);
    }

    @Test
    @DisplayName("When GetUserById With Invalid Id Then Returns Resource Not Found Exception")
    public void whenGetUserByIdThenReturnsNull(){
        //Arrange
        Long id = 1L;
        String template = "Resource %s not found for %s with value %s";
        when(userRepository.findById(id))
                .thenReturn(Optional.empty());
        String expectedMessage = String.format(template, "User", "Id", id);

        //Act
        Throwable exception = catchThrowable(()->{
            User foundUser = userService.findById(id);
        });

        //Assert
        assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(expectedMessage);
    }
    @Test
    @DisplayName("When Create a User Then Returns a new User")
    public void whenCreateUserThenReturnsNewUser(){
        //Arrange
        Long id = 1L;
        String name = "Prueba1";
        User user = new User();
        user.setId(1L);
        user.setFullName(name);

        User newUser = new User();
        newUser.setFullName(name);

        when(userRepository.save(newUser))
                .thenReturn(user);

        //Act
        User foundUser = userService.save(newUser);
        //Assert
        assertThat(foundUser.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("When Update a User Then Returns a new User")
    public void whenUpdateUserThenReturnsNewUser(){
        //Arrange
        Long id = 1L;
        String name = "Prueba1";
        User user = new User();
        user.setId(1L);
        user.setFullName(name);

        User newUser = new User();
        String name2 = "Prueba2";
        newUser.setFullName(name2);

        when(userRepository.findById(id))
                .thenReturn(Optional.of(user));

        when(userRepository.save(user))
                .thenReturn(newUser);

        //Act
        User foundUser = userService.update(id,newUser);
        //Assert
        assertThat(foundUser.getFullName()).isEqualTo(name2);
    }

    @Test
    @DisplayName("When Delete a User Then Returns a Response Entity")
    public void whenDeleteUserThenReturnsResponseEntity(){
        //Arrange
        Long id = 1L;
        String name = "Prueba1";
        User user = new User();
        user.setId(1L);
        user.setFullName(name);

        when(userRepository.findById(id))
                .thenReturn(Optional.of(user));

        //Act
        ResponseEntity<?> foundUser = userService.delete(id);
        //Assert
        assertThat(foundUser).isNotNull();
    }
}
