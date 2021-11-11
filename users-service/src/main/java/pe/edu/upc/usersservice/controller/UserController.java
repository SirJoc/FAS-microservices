package pe.edu.upc.usersservice.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.usersservice.entity.User;
import pe.edu.upc.usersservice.resource.SaveUserResource;
import pe.edu.upc.usersservice.resource.UserResource;
import pe.edu.upc.usersservice.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public Page<UserResource> getAllUsers(Pageable pageable){
        Page<User> userPage = userService.findAll(pageable);

        List<UserResource> resources = userPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());

        return new PageImpl<>(resources,pageable, resources.size());
    }

    @GetMapping("/users/{id}")
    public UserResource fetchById(@PathVariable(name = "id") Long id){
        return convertToResource(userService.findById(id));
    }
    @PostMapping("/users")
    public UserResource postUser(@Valid @RequestBody SaveUserResource resource){
        User user = convertToEntity(resource);
        return convertToResource(userService.save(user));
    }

    @PutMapping("/users/{id}")
    public UserResource updateUser(@Valid @RequestBody SaveUserResource resource,@PathVariable(name = "id") Long id){
        User user = convertToEntity(resource);
        return convertToResource(userService.update(id,user));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long id){
        return userService.delete(id);
    }

    private UserResource convertToResource(User user){return mapper.map(user,UserResource.class);}
    private User convertToEntity(SaveUserResource resource){return mapper.map(resource,User.class);}
}
