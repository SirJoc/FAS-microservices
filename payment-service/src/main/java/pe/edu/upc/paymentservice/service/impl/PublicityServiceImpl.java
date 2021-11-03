package pe.edu.upc.paymentservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.paymentservice.client.UserClient;
import pe.edu.upc.paymentservice.entities.Publicity;
import pe.edu.upc.paymentservice.model.User;
import pe.edu.upc.paymentservice.repository.PublicityRepository;
import pe.edu.upc.paymentservice.service.PublicityService;

import java.util.List;
import java.util.Optional;

@Service
public class PublicityServiceImpl implements PublicityService {
    @Autowired
    PublicityRepository publicityRepository;

    @Autowired
    UserClient userClient;

    @Override
    public Publicity save(Publicity entity) throws Exception {
        User user = userClient.fetchById(entity.getUserId());
        publicityRepository.save(entity);
        List<Publicity> publicities = getAllPublicitiesByUserId(user.getId());
        user.setPublicities(publicities);
        System.out.println("aqui estan");
        System.out.println(publicities);
        userClient.updateUser(user, entity.getUserId());
        return entity;
    }

    @Override
    public List<Publicity> findAll() throws Exception {
        return publicityRepository.findAll();
    }

    @Override
    public Optional<Publicity> findById(Long aLong) throws Exception {
        return publicityRepository.findById(aLong);
    }

    @Override
    public Publicity update(Publicity entity) throws Exception {
        return publicityRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        publicityRepository.deleteById(aLong);
    }

    @Override
    public List<Publicity> getAllPublicitiesByUserId(Long userId) throws Exception {
        return publicityRepository.findByUserId(userId);
    }
}
