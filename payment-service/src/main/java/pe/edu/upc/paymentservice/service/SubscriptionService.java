package pe.edu.upc.paymentservice.service;

import pe.edu.upc.paymentservice.entities.Publicity;
import pe.edu.upc.paymentservice.entities.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService{
    public Subscription save(Subscription entity);
    public List<Subscription> findAll() throws Exception;
    public Optional<Subscription> findById(Long aLong) throws Exception;
    public Subscription update(Long id, Subscription entity) throws Exception;
    public void deleteById(Long aLong) throws Exception;
    public List<Subscription> getAllSubscriptionsByUserId(Long userId) throws Exception;
}
