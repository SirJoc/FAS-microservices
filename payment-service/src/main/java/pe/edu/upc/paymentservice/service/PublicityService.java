package pe.edu.upc.paymentservice.service;

import pe.edu.upc.paymentservice.entities.Publicity;

import java.util.List;
import java.util.Optional;

public interface PublicityService {
    public Publicity save(Long userId, Publicity entity) throws Exception;
    public List<Publicity> findAll() throws Exception;
    public Optional<Publicity> findById(Long aLong) throws Exception;
    public Publicity update(Publicity entity) throws Exception;
    public void deleteById(Long aLong) throws Exception;
    public List<Publicity> getAllPublicitiesByUserId(Long userId) throws Exception;
}
