package fi.eatech.fleetmanagerws.controller;

import fi.eatech.fleetmanagerws.model.Car;
import fi.eatech.fleetmanagerws.model.tools.CarFilter;
import fi.eatech.fleetmanagerws.model.tools.Tools;
import fi.eatech.fleetmanagerws.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Car controller
 *
 * @author Ville Nupponen
 * @since 1.0.0
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
    public List<Car> list(CarFilter carFilter, @RequestParam(required = false, defaultValue = "") String filters) {
        return carService.getCars(filters.isEmpty() ? carFilter : Tools.convertJsonToCarFilter(filters));
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
