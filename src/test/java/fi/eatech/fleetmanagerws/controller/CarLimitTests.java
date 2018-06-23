package fi.eatech.fleetmanagerws.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.eatech.fleetmanagerws.TestUtils;
import fi.eatech.fleetmanagerws.model.Car;
import fi.eatech.fleetmanagerws.model.tools.CarFilter;
import fi.eatech.fleetmanagerws.repository.CarRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for car's filters
 *
 * @author Ville Nupponen
 * @since 0.0.1-SNAPSHOT
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CarLimitTests {

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
        carRepository.save(cars);
    }

    @Test
    public void testModelYearLimit() throws Exception {
        int modelYearMin = 1900 + (int)(Math.random() * (Calendar.getInstance().get(Calendar.YEAR) - 1900));
        int modelYearMax = modelYearMin + (int)(Math.random() * (Calendar.getInstance().get(Calendar.YEAR) - modelYearMin));

        CarFilter carFilter1 = new CarFilter(modelYearMin, null, null, null);
        CarFilter carFilter2 = new CarFilter(null, modelYearMax, null, null);
        CarFilter carFilter3 = new CarFilter(modelYearMin, modelYearMax, null, null);

        MvcResult result = mockMvc.perform(get("/car/")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .param("filters", TestUtils.convertObjectToJsonString(carFilter1))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andReturn();

        List<Car> cars = TestUtils.convertJsonStringToObject(result.getResponse().getContentAsString(), new TypeReference<List<Car>>(){});
        Assert.assertTrue(
                String.format("Rajoite '%s' palautti väärän tuloksen", carFilter1),
                testYearLimit(cars, carFilter1.getModelYearMin(), carFilter1.getModelYearMax()));

        result = mockMvc.perform(get("/car/")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .param("filters", TestUtils.convertObjectToJsonString(carFilter2))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andReturn();

        cars = TestUtils.convertJsonStringToObject(result.getResponse().getContentAsString(), new TypeReference<List<Car>>(){});
        Assert.assertTrue(
                String.format("Rajoite '%s' palautti väärän tuloksen", carFilter2),
                testYearLimit(cars, carFilter2.getModelYearMin(), carFilter2.getModelYearMax()));

        Assert.assertTrue(testYearLimit(cars, carFilter2.getModelYearMin(), carFilter2.getModelYearMax()));

        result = mockMvc.perform(get("/car/")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .param("filters", TestUtils.convertObjectToJsonString(carFilter3))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andReturn();

        cars = TestUtils.convertJsonStringToObject(result.getResponse().getContentAsString(), new TypeReference<List<Car>>(){});
        Assert.assertTrue(
                String.format("Rajoite '%s' palautti väärän tuloksen", carFilter3),
                testYearLimit(cars, carFilter3.getModelYearMin(), carFilter3.getModelYearMax()));

    }

    private boolean testYearLimit(List<Car> cars, Integer minYear, Integer maxYear) {
        for (Car car : cars) {
            if (minYear != null && car.getModelYear() < minYear) return false;
            if (maxYear != null && car.getModelYear() > maxYear) return false;
        }
        return true;
    }

    @Test
    public void testBrandLimit() throws Exception {
        String brand = "Subaru";
        CarFilter carFilter = new CarFilter(null, null, null, brand);

        MvcResult result = mockMvc.perform(get("/car/")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .param("filters", TestUtils.convertObjectToJsonString(carFilter))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andReturn();

        List<Car> cars = TestUtils.convertJsonStringToObject(result.getResponse().getContentAsString(), new TypeReference<List<Car>>(){});
        Assert.assertTrue(
                String.format("Rajoite '%s' palautti väärän tuloksen", carFilter),
                cars.stream().anyMatch(i -> !i.getBrand().toLowerCase().equals(brand.toLowerCase())));
    }

    @Test
    public void testModelLimit() throws Exception {
        String model = "GT-S";
        CarFilter carFilter = new CarFilter(null, null, model, null);

        MvcResult result = mockMvc.perform(get("/car/")
                .contentType(TestUtils.APPLICATION_JSON_UTF8)
                .param("filters", TestUtils.convertObjectToJsonString(carFilter))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andReturn();

        List<Car> cars = TestUtils.convertJsonStringToObject(result.getResponse().getContentAsString(), new TypeReference<List<Car>>(){});
        Assert.assertTrue(
                String.format("Rajoite '%s' palautti väärän tuloksen", carFilter),
                cars.stream().anyMatch(i -> !i.getModel().toLowerCase().equals(model.toLowerCase())));
    }
}
