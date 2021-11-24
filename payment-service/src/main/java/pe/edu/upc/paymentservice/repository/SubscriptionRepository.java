package pe.edu.upc.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.paymentservice.entities.Publicity;
import pe.edu.upc.paymentservice.entities.Subscription;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    public List<Subscription> findByUserId(Long userId);
}
