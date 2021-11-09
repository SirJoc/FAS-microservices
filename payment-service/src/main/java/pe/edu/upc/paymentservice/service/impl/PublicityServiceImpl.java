package pe.edu.upc.paymentservice.service.impl;

import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
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
    public Publicity save(Publicity entity) {
        User user = userClient.fetchById(entity.getUserId()).getBody();
        if (user != null) {
            publicityRepository.save(entity);
        }
        return entity;
    }

    @Override
    public List<Publicity> findAll() throws Exception {
        List<Publicity> publicities = publicityRepository.findAll();
        for (int i = 0; i < publicities.size(); i++) {
            Publicity publicity = publicities.get(i);
            User user = userClient.fetchById(publicity.getUserId()).getBody();
            publicities.get(i).setUser(user);
        }
        return publicities;
    }

    @Override
    public Optional<Publicity> findById(Long aLong) throws Exception {
        Optional<Publicity> publicity = publicityRepository.findById(aLong);
        if(publicity.isPresent()) {
            User user = userClient.fetchById(publicity.get().getUserId()).getBody();
            publicity.get().setUser(user);
        }
        return publicity;
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
    public List<Publicity> getAllPublicitiesByUserId(Long userId) {
        return publicityRepository.findByUserId(userId);
    }
}
