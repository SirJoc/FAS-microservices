package pe.edu.upc.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.paymentservice.entities.Publicity;

import java.util.List;

public interface PublicityRepository extends JpaRepository<Publicity, Long> {
    public List<Publicity> findByUserId(Long userId);
}
