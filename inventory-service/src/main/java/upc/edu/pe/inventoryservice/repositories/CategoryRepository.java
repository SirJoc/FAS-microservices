package upc.edu.pe.inventoryservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.inventoryservice.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
