package pe.edu.upc.reviewservice;

import org.apache.coyote.Response;
import org.apache.http.protocol.HTTP;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pe.edu.upc.reviewservice.client.ProductClient;
import pe.edu.upc.reviewservice.client.UserClient;
import pe.edu.upc.reviewservice.entity.Comment;
import pe.edu.upc.reviewservice.model.Product;
import pe.edu.upc.reviewservice.model.User;
import pe.edu.upc.reviewservice.repository.CommentRepository;
import pe.edu.upc.reviewservice.service.CommentService;
import pe.edu.upc.reviewservice.service.CommentServiceImpl;
import pe.edu.upc.reviewservice.exceptions.ResourceNotFoundException;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ReviewServiceImplIntegrationTest {
    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    @Qualifier("pe.edu.upc.reviewservice.client.UserClient")
    private UserClient userClient;

    @MockBean
    @Qualifier("pe.edu.upc.reviewservice.client.ProductClient")
    private ProductClient productClient;

    @Autowired
    private CommentService commentService;

    @TestConfiguration
    static class CommentImplTestConfiguration {
        @Bean
        public CommentService commentServiceService(){
            return new CommentServiceImpl();
        }
    }

    @Test
    @DisplayName("When GetCommentById With Valid Id Then Returns Comment Profile")
    public void whenGetCommentByIdThenReturnsComment(){
        //Arrange
        Long id = 1L;
        String message = "Message1";
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setMessage(message);

        when(commentRepository.findById(id)).thenReturn(Optional.of(comment));

        //Act
        Comment foundComment = commentService.findById(id);
        //Assert
        assertThat(foundComment.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("When GetCommentById With Invalid Id Then Returns Resource Not Found Exception")
    public void whenGetCommentByIdThenReturnsNull(){
        //Arrange
        Long id = 1L;
        String template = "Resource %s not found for %s with value %s";
        when(commentRepository.findById(id))
                .thenReturn(Optional.empty());
        String expectedMessage = String.format(template, "Comment", "Id", id);

        //Act
        Throwable exception = catchThrowable(()->{
            Comment foundComment = commentService.findById(id);
        });

        //Assert
        assertThat(exception)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(expectedMessage);
    }
    @Test
    @DisplayName("When Create a Comment Then Returns a new Comment")
    public void whenCreateCommentThenReturnsNewComment(){
        //Arrange
        Long id = 1L;
        String message = "Message1";
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setMessage(message);

        Comment newComment = new Comment();
        newComment.setMessage(message);

        Long userId = 1L;
        String userName = "User1";
        User user = User.builder()
                .id(1L)
                .fullName(userName).build();
        comment.setUser(user);
        ResponseEntity<User> userResponse = ResponseEntity.of(Optional.of(user));

        when(userClient.getUser(userId))
                .thenReturn(userResponse);

        Long productId = 1L;
        String productName = "Product1";
        Product product = Product.builder()
                        .id(productId)
                        .name(productName).build();
        comment.setProduct(product);
        ResponseEntity<Product> productResponse = ResponseEntity.of(Optional.of(product));

        when(productClient.getProduct(productId))
                .thenReturn(productResponse);

        when(commentRepository.save(newComment))
                .thenReturn(comment);

        //Act
        Comment foundComment = commentService.create(userId,productId,newComment);
        //Assert
        assertThat(foundComment.getProduct()).isEqualTo(product);
    }

    @Test
    @DisplayName("When Update a Comment Then Returns a new Comment")
    public void whenUpdateCommentThenReturnsNewComment(){
        //Arrange
        Long id = 1L;
        String message = "Message1";
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setMessage(message);

        Comment newComment = new Comment();
        String message2 = "Message2";
        newComment.setMessage(message2);

        when(commentRepository.findById(id))
                .thenReturn(Optional.of(comment));

        when(commentRepository.save(comment))
                .thenReturn(newComment);

        //Act
        Comment foundComment = commentService.update(id,newComment);
        //Assert
        assertThat(foundComment.getMessage()).isEqualTo(message2);
    }

    @Test
    @DisplayName("When Delete a Comment Then Returns a Response Entity")
    public void whenDeleteCommentThenReturnsResponseEntity(){
        //Arrange
        Long id = 1L;
        String message = "Message";
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setMessage(message);

        when(commentRepository.findById(id))
                .thenReturn(Optional.of(comment));

        //Act
        ResponseEntity<?> foundComment = commentService.delete(id);
        //Assert
        assertThat(foundComment).isNotNull();
    }
}
