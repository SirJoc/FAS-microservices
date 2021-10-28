package pe.edu.upc.locationservice.service.Impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.locationservice.models.Coordinate;
import pe.edu.upc.locationservice.repository.CoordinateRepository;
import pe.edu.upc.locationservice.service.CoordinateService;

import java.util.List;

@Service
public class CoordinateServiceImpl implements CoordinateService {
    @Autowired
    private CoordinateRepository coordinateRepository;

    @Override
    public List<Coordinate> listAllCoordinate() {
        return coordinateRepository.findAll();
    }

    @Override
    public Coordinate getCoordinateById(Long id) {
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
