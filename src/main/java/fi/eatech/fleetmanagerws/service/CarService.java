package fi.eatech.fleetmanagerws.service;

import fi.eatech.fleetmanagerws.exceptions.BadRequestException;
import fi.eatech.fleetmanagerws.exceptions.NotFoundException;
import fi.eatech.fleetmanagerws.model.Car;
import fi.eatech.fleetmanagerws.model.tools.CarFilter;
import fi.eatech.fleetmanagerws.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Car> getCars(CarFilter filters) {
        List<Car> cars = new ArrayList<>();
        for (Car car : carRepository.findAll()) {
            if (filters.getModelYearMin() != null && filters.getModelYearMin() > car.getModelYear()) continue;
            if (filters.getModelYearMax() != null && filters.getModelYearMax() < car.getModelYear()) continue;
            if (filters.getModel() != null && !filters.getModel().toLowerCase().equals(car.getModel().toLowerCase())) continue;
            if (filters.getBrand() != null && !filters.getBrand().toLowerCase().equals(car.getBrand().toLowerCase())) continue;
            cars.add(car);
        }
        return cars;
    }

    public Car addCar(Car car) {
        if (carRepository.getOne(car.getRegistrationNumber()) != null)
            throw new BadRequestException("Can't add new car when registrationNumber is already used.");
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
