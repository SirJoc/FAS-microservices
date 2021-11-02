package pe.edu.upc.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.paymentservice.entities.Publicity;

import java.util.List;

public interface PublicityRepository extends JpaRepository<Publicity, Long> {
    public List<Publicity> findByUserId(Long userId);
}
