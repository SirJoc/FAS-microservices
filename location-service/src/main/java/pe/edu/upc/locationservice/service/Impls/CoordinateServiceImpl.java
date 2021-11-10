package pe.edu.upc.locationservice.service.Impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.locationservice.client.UserClient;
import pe.edu.upc.locationservice.entity.Coordinate;
import pe.edu.upc.locationservice.model.User;
import pe.edu.upc.locationservice.repository.CoordinateRepository;
import pe.edu.upc.locationservice.service.CoordinateService;

import java.util.List;
import java.util.Optional;

@Service
public class CoordinateServiceImpl implements CoordinateService {
    @Autowired
    private CoordinateRepository coordinateRepository;

    @Autowired
    UserClient userClient;

    @Override
    public List<Coordinate> listAllCoordinate() {
        List<Coordinate> coordinates = coordinateRepository.findAll();
        for (int i = 0; i < coordinates.size(); i++) {
            Coordinate publicity = coordinates.get(i);
            User user = userClient.fetchById(publicity.getUserId()).getBody();
            coordinates.get(i).setUser(user);
        }
        return coordinates;
    }

    @Override
    public Coordinate getCoordinateById(Long id) {
        Optional<Coordinate> coordinate = coordinateRepository.findById(id);
        if (coordinate.isPresent()){
            User user = userClient.fetchById(coordinate.get().getUserId()).getBody();
            coordinate.get().setUser(user);
        }
        return coordinateRepository.findById(id).orElse(null);
    }

    @Override
    public Coordinate createCoordinate(Coordinate coordinate) {
        coordinate.setStatus("CREATED");
        return coordinateRepository.save(coordinate);
    }

    @Override
    public Coordinate updateCoordinate(Coordinate coordinateRequest) {
        Coordinate coordinateDB = getCoordinateById(coordinateRequest.getId());
        if (null == coordinateDB){
            return null;
        }
        coordinateDB.setAltitude(coordinateRequest.getAltitude());
        coordinateDB.setDescription(coordinateRequest.getDescription());
        coordinateDB.setLatitude(coordinateRequest.getLatitude());
        coordinateDB.setLongitude(coordinateRequest.getLongitude());
        coordinateDB.setFavorite_route(coordinateRequest.getFavorite_route());
        return coordinateRepository.save(coordinateDB);
    }

    @Override
    public Coordinate deleteCoordinateById(Long id) {
        Coordinate coordinateDB = getCoordinateById(id);
        if (null == coordinateDB){
            return  null;
        }
        coordinateDB.setStatus("DELETED");
        return coordinateRepository.save(coordinateDB);
    }
}
