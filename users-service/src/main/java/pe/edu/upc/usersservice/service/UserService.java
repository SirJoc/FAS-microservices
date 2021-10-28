package pe.edu.upc.usersservice.service;

import pe.edu.upc.usersservice.entity.User;

public interface UserService extends CrudService<User,Long> {
    User save(User userProfile);
}
