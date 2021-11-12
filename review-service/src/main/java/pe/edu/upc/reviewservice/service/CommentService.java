package pe.edu.upc.reviewservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import pe.edu.upc.reviewservice.entity.Comment;

public interface CommentService {
    Page<Comment> findAll(Pageable pageable);
    Page<Comment> findAllByUserId(Pageable pageable,Long userId);
    Page<Comment> findAllByProductId(Pageable pageable,Long productId);
    Comment create(Long userId, Long productId, Comment comment);
    Comment findById(Long id);
    Comment update(Long id, Comment newComment);
    ResponseEntity<?> delete(Long id);
}
