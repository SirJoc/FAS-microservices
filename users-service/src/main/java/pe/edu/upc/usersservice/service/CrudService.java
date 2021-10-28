package pe.edu.upc.usersservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CrudService <T,ID>{
    Page<T> findAll(Pageable pageable);
    T findById(ID id);
    T update(ID id,T newEntity);
    ResponseEntity<?> delete(ID id) ;
}
