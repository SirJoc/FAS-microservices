package pe.edu.upc.usersservice.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.edu.upc.usersservice.entity.User;
import pe.edu.upc.usersservice.exceptions.ResourceNotFoundException;
import pe.edu.upc.usersservice.repository.UserRepository;
import pe.edu.upc.usersservice.service.UserService;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",id));
    }

    @Transactional()
    @Override
    public User update(Long id,User newEntity) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",id));

        user.setFullName(newEntity.getFullName());
        user.setEmail(newEntity.getEmail());
        user.setPassword(newEntity.getPassword());
        user.setRuc(newEntity.getRuc());
        System.out.println("AQui esta todo");
        return userRepository.save(user);
    }

    @Transactional()
    @Override
    public ResponseEntity<?> delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",id));

        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

    @Transactional()
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
