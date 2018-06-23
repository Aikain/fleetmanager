package fi.eatech.fleetmanagerws.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.eatech.fleetmanagerws.TestUtils;
import fi.eatech.fleetmanagerws.model.Car;
import fi.eatech.fleetmanagerws.repository.CarRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for car controller
 *
 * @author Ville Nupponen
 * @since 0.0.1-SNAPSHOT
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CarTests {

    private MockMvc mockMvc;

    private WebApplicationContext webApplicationContext;
    private CarRepository carRepository;

    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    @Autowired
    public void setCarRepository(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Before
    public void setUp() throws IOException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        carRepository.deleteAll();
        List<Car> cars = new ObjectMapper().readValue(
                TypeReference.class.getResourceAsStream("/data.json"), new TypeReference<List<Car>>(){});
        carRepository.saveAll(cars);
    }

    @Test
    public void testCarList() throws Exception {
        MvcResult result = mockMvc.perform(get("/car/")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andReturn();

        List<Car> cars = TestUtils.convertJsonStringToObject(result.getResponse().getContentAsString(), new TypeReference<List<Car>>(){});
        Assert.assertEquals(
                "Palautettujen autojen määrä ei vastaa tietokannassa olevien määrää!",
                cars.size(), carRepository.findAll().size());
    }

    @Test
    public void testCarGet() throws Exception {
        List<Car> cars = carRepository.findAll();
        Car car = cars.get((int)(Math.random() * cars.size()));

        MvcResult result = mockMvc.perform(get("/car/" + car.getRegistrationNumber())
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andReturn();

        Car carFromREST = TestUtils.convertJsonStringToObject(result.getResponse().getContentAsString(), new TypeReference<Car>(){});
        Assert.assertTrue(
                "Palautettu auto ei ole sama kuin jonka tietojen perusteella auto pyydettiin!",
                car.equals(carFromREST) && car.toString().equals(carFromREST.toString()));
    }

    @Test
    public void testCarAdd() throws Exception {
        List<Car> cars = carRepository.findAll();
        Car car = new Car("RST-987", "Toyota", "New", (short)2000, new Date(), 1.5, 100.0);

        mockMvc.perform(post("/car/")
                .content(TestUtils.convertObjectToJsonString(car))
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8));

        Assert.assertEquals(
                "Tietokannassa pitäisi olla yksi auto enemmän kuin ennen lisäämistä!",
                cars.size() + 1, carRepository.count());
        Assert.assertNotNull(
                "Tietokannasta pitäisi löytyä auto lisätyn auton rekisterinumeron perusteella!",
                carRepository.getOne(car.getRegistrationNumber()));
    }

    @Test
    public void testCarUpdate() throws Exception {
        List<Car> cars = carRepository.findAll();
        Car car = cars.get((int)(Math.random() * cars.size()));
        Car newCarInfo = new Car(car.getRegistrationNumber(), "Toyota", "New", (short)2000, new Date(), 1.5, 100.0);

        mockMvc.perform(put("/car/" + car.getRegistrationNumber())
                .content(TestUtils.convertObjectToJsonString(newCarInfo))
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8));

        Car updatedCar = carRepository.getOne(car.getRegistrationNumber());

        Assert.assertEquals(
                "Tietokannassa pitäisi olla saman verran autoja kuin ennen päivitystä!",
                cars.size(), carRepository.count());
        Assert.assertNotNull(
                "Tietokannasta pitäisi löytyä auto muokatun auton rekisterinumeron perusteella!",
                updatedCar);
        Assert.assertEquals(
                "Päivitetyt tiedot eivät vastaa tietokannassa olevia!",
                updatedCar.toString(), newCarInfo.toString());
    }

    @Test
    public void testCarDelete() throws Exception {
        List<Car> cars = carRepository.findAll();
        Car car = cars.get((int)(Math.random() * cars.size()));

        mockMvc.perform(delete("/car/" + car.getRegistrationNumber())
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8));

        Assert.assertEquals(
                "Tietokannassa pitäisi olla yksi auto vähemmän kuin ennen poistamista!",
                cars.size() - 1, carRepository.count());
        Assert.assertFalse(
                "Tietokannasta ei pitäisi löytyä autoa poistetun auton rekisterinumeron perusteella",
                carRepository.existsById(car.getRegistrationNumber()));
    }
}
