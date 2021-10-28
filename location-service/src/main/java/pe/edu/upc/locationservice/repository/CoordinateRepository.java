package pe.edu.upc.locationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.locationservice.models.Coordinate;

@Repository
public interface CoordinateRepository extends JpaRepository<Coordinate, Long> {

}
