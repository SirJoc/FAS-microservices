package upc.edu.pe.inventoryservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.inventoryservice.entities.Product;



@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
