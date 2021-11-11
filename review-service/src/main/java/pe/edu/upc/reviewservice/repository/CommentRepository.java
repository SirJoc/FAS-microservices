package pe.edu.upc.reviewservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.reviewservice.entity.Comment;
public interface CommentRepository extends JpaRepository<Comment,Long> {
    Page<Comment> findAllByUserId(Pageable pageable, Long userId);
    Page<Comment> findAllByProductId(Pageable pageable, Long productId);
}
