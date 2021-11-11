package pe.edu.upc.reviewservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.edu.upc.reviewservice.client.ProductClient;
import pe.edu.upc.reviewservice.client.UserClient;
import pe.edu.upc.reviewservice.entity.Comment;
import pe.edu.upc.reviewservice.model.Product;
import pe.edu.upc.reviewservice.model.User;
import pe.edu.upc.reviewservice.repository.CommentRepository;

import org.springframework.data.domain.Pageable;
import pe.edu.upc.usersservice.exceptions.ResourceNotFoundException;

import java.util.Objects;


@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;

    @Qualifier("pe.edu.upc.reviewservice.client.UserClient")
    @Autowired
    private UserClient userClient;

    @Qualifier("pe.edu.upc.reviewservice.client.ProductClient")
    @Autowired
    private ProductClient productClient;

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Page<Comment> findAllByUserId(Pageable pageable, Long userId) {
        return commentRepository.findAllByUserId(pageable,userId);
    }

    @Override
    public Page<Comment> findAllByProduct(Pageable pageable, Long productId) {
        return commentRepository.findAllByProductId(pageable,productId);
    }

    @Override
    public Comment create(Long userId, Long productId, Comment comment) {
        if(Objects.requireNonNull(userClient.getUser(userId).getBody()).getId() == null){
            throw new ResourceNotFoundException("User","Id", userId);
        }
        if(Objects.requireNonNull(productClient.getProduct(productId).getBody()).getId() == null){
            throw new ResourceNotFoundException("Product","Id", productId);
        }
        User user = userClient.getUser(userId).getBody();
        Product product = productClient.getProduct(productId).getBody();
        comment.setUser(user);
        comment.setProduct(product);
        return commentRepository.save(comment);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Comment","Id",id));
    }

    @Override
    public Comment update(Long id, Comment newComment) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Comment","Id",id));
        comment.setMessage(newComment.getMessage());
        return commentRepository.save(comment);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Comment","Id",id));
        commentRepository.delete(comment);
        return ResponseEntity.ok().build();
    }
}
