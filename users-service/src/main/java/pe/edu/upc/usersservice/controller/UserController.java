package pe.edu.upc.usersservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.usersservice.entity.User;
import pe.edu.upc.usersservice.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public Page<User> getAllUsers(Pageable pageable){
        return userService.findAll(pageable);
    }

    @GetMapping("/users/{id}")
    public User fetchById(@PathVariable(name = "id") Long id){
        return userService.findById(id);
    }
    @PostMapping("/users")
    public User postUser(@Valid @RequestBody User user){
        return userService.save(user);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@Valid @RequestBody User user,@PathVariable(name = "id") Long id){
        return userService.update(id, user);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long id){
        return userService.delete(id);
    }
}
