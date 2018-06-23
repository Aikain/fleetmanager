package fi.eatech.fleetmanagerws.service;

import fi.eatech.fleetmanagerws.exceptions.NotFoundException;
import fi.eatech.fleetmanagerws.model.Car;
import fi.eatech.fleetmanagerws.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Car service
 *
 * @author Ville Nupponen
 * @since 0.0.1-SNAPSHOT
 */
@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    public Car getCar(String id) {
        Car car = carRepository.getOne(id);
        if (car == null) throw new NotFoundException("Car not found with given registrationnumber!");
        return car;
    }

    public Car updateCar(String id, Car car) {
        getCar(id);
        car.setRegistrationNumber(id);
        return carRepository.save(car);
    }

    public Car deleteCar(String id) {
        Car car = getCar(id);
        carRepository.delete(id);
        return car;
    }
}