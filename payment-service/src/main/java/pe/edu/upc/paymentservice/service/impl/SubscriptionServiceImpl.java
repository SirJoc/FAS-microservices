package pe.edu.upc.paymentservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.paymentservice.entities.Subscription;
import pe.edu.upc.paymentservice.repository.SubscriptionRepository;
import pe.edu.upc.paymentservice.service.SubscriptionService;

import java.util.List;
import java.util.Optional;


@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Transactional
    @Override
    public Subscription save(Subscription entity) throws Exception {
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
    public Subscription update(Subscription entity) throws Exception {
        return subscriptionRepository.save(entity);
    }

    @Transactional
    @Override
    public void deleteById(Long aLong) throws Exception {
        subscriptionRepository.deleteById(aLong);
    }
}
