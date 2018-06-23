package fi.eatech.fleetmanagerws.controller;

import fi.eatech.fleetmanagerws.model.Car;
import fi.eatech.fleetmanagerws.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Car controller
 *
 * @author Ville Nupponen
 * @since 0.0.1-SNAPSHOT
 */
@RequestMapping("/car")
@RestController
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Car> list() {
        return carService.getCars();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Car add(@RequestBody Car car) {
        return carService.addCar(car);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Car get(@PathVariable String id) {
        return carService.getCar(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Car update(@PathVariable String id, @RequestBody Car car) {
        return carService.updateCar(id, car);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Car delete(@PathVariable String id) {
        return carService.deleteCar(id);
    }

}
