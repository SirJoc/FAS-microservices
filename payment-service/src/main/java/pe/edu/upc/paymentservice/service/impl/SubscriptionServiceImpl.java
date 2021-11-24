package pe.edu.upc.paymentservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.paymentservice.client.UserClient;
import pe.edu.upc.paymentservice.entities.Publicity;
import pe.edu.upc.paymentservice.entities.Subscription;
import pe.edu.upc.paymentservice.model.User;
import pe.edu.upc.paymentservice.repository.SubscriptionRepository;
import pe.edu.upc.paymentservice.service.SubscriptionService;

import java.util.List;
import java.util.Optional;


@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    SubscriptionRepository subscriptionRepository;
    @Autowired
    UserClient userClient;

    @Transactional
    @Override
    public Subscription save(Subscription entity) {
        return subscriptionRepository.save(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Subscription> findAll() throws Exception {
        return subscriptionRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Subscription> findById(Long aLong) throws Exception {
        return subscriptionRepository.findById(aLong);
    }

    @Transactional
    @Override
    public Subscription update(Long id, Subscription entity) throws Exception {
        Subscription subscription = subscriptionRepository.findById(id).get();
        subscription.setUserId(entity.getUserId());
        subscription.setType(entity.getType());
        return subscriptionRepository.save(subscription);
    }

    @Transactional
    @Override
    public void deleteById(Long aLong) throws Exception {
        subscriptionRepository.deleteById(aLong);
    }

    @Override
    public List<Subscription> getAllSubscriptionsByUserId(Long userId) throws Exception {
        List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);
        for (int i = 0; i < subscriptions.size(); i++) {
            Subscription subscription = subscriptions.get(i);
            User user = userClient.fetchById(subscription.getUserId()).getBody();
            subscriptions.get(i).setUser(user);
        }
        return subscriptions;
    }
}
