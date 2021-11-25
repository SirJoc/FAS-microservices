package pe.edu.upc.locationservice.service;

import pe.edu.upc.locationservice.entity.Coordinate;

import java.util.List;

public interface CoordinateService {
    public List<Coordinate> listAllCoordinate();
    public Coordinate getCoordinateById(Long id);
    public Coordinate createCoordinate(Coordinate coordinate);
    public Coordinate updateCoordinate(Coordinate coordinate);
    public Coordinate deleteCoordinateById(Long id);
}

