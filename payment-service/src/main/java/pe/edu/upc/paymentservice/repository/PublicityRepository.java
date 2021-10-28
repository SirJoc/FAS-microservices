package pe.edu.upc.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.paymentservice.entities.Publicity;

public interface PublicityRepository extends JpaRepository<Publicity, Long> {
}
