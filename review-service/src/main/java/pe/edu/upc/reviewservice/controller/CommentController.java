package pe.edu.upc.reviewservice.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.reviewservice.entity.Comment;
import pe.edu.upc.reviewservice.resources.CommentResource;
import pe.edu.upc.reviewservice.resources.SaveCommentResource;
import pe.edu.upc.reviewservice.service.CommentService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    public Page<CommentResource> getAllComments(Pageable pageable){
        Page<Comment> commentPage = commentService.findAll(pageable);

        List<CommentResource> resources = commentPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());

        return new PageImpl<>(resources,pageable, resources.size());
    }

    @GetMapping("/comments/{id}")
    public CommentResource fetchById(@PathVariable(name = "id") Long id){
        return convertToResource(commentService.findById(id));
    }
    @PostMapping("/comments")
    public CommentResource postComment(@Valid @RequestBody SaveCommentResource resource,
                                       @RequestParam("product") Long productId,
                                       @RequestParam("user") Long userId){
        Comment comment = convertToEntity(resource);
        return convertToResource(commentService.create(userId,productId,comment));
    }

    @PutMapping("/comments/{id}")
    public CommentResource updateComment(@Valid @RequestBody SaveCommentResource resource,@PathVariable(name = "id") Long id){
        Comment comment = convertToEntity(resource);
        return convertToResource(commentService.update(id,comment));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "id") Long id){
        return commentService.delete(id);
    }


    private CommentResource convertToResource(Comment comment){return mapper.map(comment,CommentResource.class);}
    private Comment convertToEntity(SaveCommentResource resource){return mapper.map(resource,Comment.class);}
}
