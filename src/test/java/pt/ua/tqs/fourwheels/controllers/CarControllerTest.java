package pt.ua.tqs.fourwheels.controllers;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pt.ua.tqs.fourwheels.authentication.JwtTokenUtil;
import pt.ua.tqs.fourwheels.entities.Car;
import pt.ua.tqs.fourwheels.repositories.CarRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarRepository carRepository;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    private String accessToken = System.getenv("TEST_TOKEN");
    private String email = System.getenv("TESTER_EMAIL");


    private Car car;
    private JSONObject json;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CarController(carRepository, jwtTokenUtil))
                .alwaysExpect(forwardedUrl(null))
                .build();

        car = new Car();
        car.setCarState("selling");
        car.setId(1);
        car.setPhoto("IMAGEMFANTAASTICA");
        car.setBrand("Mazda");
        car.setModel("555");
        car.setYear(1999);
        car.setMonth(5);
        car.setDescription("EUSOU Lesias");
        car.setKilometers(9412);
        car.setTypeOfFuel("Gasolina");
        car.setOwnerMail(email);
        car.setPrice(12003);

        json = new JSONObject();
        JSONObject pre = new JSONObject();
        pre.put("id", car.getId());
        pre.put("photo", car.getPhoto());
        pre.put("brand", car.getBrand());
        pre.put("model",car.getModel());
        pre.put("year", car.getYear());
        pre.put("month",car.getMonth());
        pre.put("description",car.getDescription());
        pre.put("kilometers", car.getKilometers());
        pre.put("typeOfFuel", car.getTypeOfFuel());
        pre.put("ownerMail", car.getOwnerMail());
        pre.put("price", car.getPrice());
        pre.put("carState", car.getCarState());
        json.put("car",pre);
    }

    @Test
    public void getCarInfoWithToken() throws Exception {
        // Mocks
        Mockito.when(carRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(car));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(carRepository.findCarsById(1)).thenReturn(car);

        mockMvc.perform(get("/car/"+ car.getId()).header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("id")));

        MvcResult mock = mockMvc.perform(get("/car/"+ car.getId()).header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void getCarInfoWithoutToken() throws Exception{
        // Mocks
        Mockito.when(carRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(car));
        Mockito.when(carRepository.findCarsById(1)).thenReturn(car);

        mockMvc.perform(get("/car/" + car.getId())).andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasKey("id")));

        MvcResult mock = mockMvc.perform(get("/car/"+ car.getId()))
                .andExpect(status().is(200))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void getAllCarsWithToken() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        Page<Car> carPage = new PageImpl<Car>(cars.subList(0,1),Pageable.unpaged(),cars.size());
        // Mocks
        Mockito.when(carRepository.findCarsByCarStateEquals("selling", PageRequest.of(1,1))).thenReturn(carPage);
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);

        mockMvc.perform(get("/car/?page=1&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("data")));

        MvcResult mock = mockMvc.perform(get("/car/?page=1&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void getAllCarsWithoutToken() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        Page<Car> carPage = new PageImpl<Car>(cars.subList(0,1),Pageable.unpaged(),cars.size());
        // Mocks
        Mockito.when(carRepository.findCarsByCarStateEquals("selling", PageRequest.of(1,1))).thenReturn(carPage);

        mockMvc.perform(get("/car/?page=1&limit=1")).andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasKey("data")));

        MvcResult mock = mockMvc.perform(get("/car/?page=1&limit=1"))
                .andExpect(status().is(200))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void insertCarWithToken()  throws Exception {
        // Mocks
        Mockito.when(carRepository.save(car)).thenReturn(car);
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);

        mockMvc.perform(post("/car/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(String.valueOf(json))
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mock = mockMvc.perform(post("/car/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(String.valueOf(json))
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void insertCarWithWrongToken()  throws Exception {
        // Mocks
        Mockito.when(carRepository.save(car)).thenReturn(car);
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn("");

        mockMvc.perform(post("/car/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(String.valueOf(json))
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(post("/car/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(String.valueOf(json))
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void insertCarWithoutToken()  throws Exception {
        // Mocks
        mockMvc.perform(post("/car/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(String.valueOf(json)))
                .andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(post("/car/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(String.valueOf(json)))
                .andDo(print())
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void deleteCarWithToken() throws  Exception {
        // Mocks
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);

        mockMvc.perform(delete("/car/"+ car.getId())
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mock = mockMvc.perform(delete("/car/"+ car.getId())
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void deleteCarWithInvalidToken() throws  Exception {
        // Mocks
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn("");

        mockMvc.perform(delete("/car/"+ car.getId())
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(delete("/car/"+ car.getId())
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void deleteCarWithoutToken() throws  Exception {
        // Mocks

        mockMvc.perform(delete("/car/"+ car.getId()))
                .andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(delete("/car/"+ car.getId()))
                .andDo(print())
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void searchByBrandWithToken() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        Page<Car> carPage = new PageImpl<Car>(cars.subList(0,1),Pageable.unpaged(),cars.size());
        // Mocks
        Mockito.when(carRepository.findCarsByBrandContainingAndCarStateEquals(car.getBrand(),"selling", PageRequest.of(1,1))).thenReturn(carPage);
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);

        mockMvc.perform(get("/car/brand/"+car.getBrand()+"?page=1&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("data")));

        MvcResult mock = mockMvc.perform(get("/car/brand/"+car.getBrand()+"?page=1&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void searchByBrandWithoutToken() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        Page<Car> carPage = new PageImpl<Car>(cars.subList(0,1),Pageable.unpaged(),cars.size());
        // Mocks
        Mockito.when(carRepository.findCarsByBrandContainingAndCarStateEquals(car.getBrand(),"selling", PageRequest.of(1,1))).thenReturn(carPage);

        mockMvc.perform(get("/car/brand/"+car.getBrand()+"?page=1&limit=1")).andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasKey("data")));

        MvcResult mock = mockMvc.perform(get("/car/brand/"+car.getBrand()+"?page=1&limit=1"))
                .andExpect(status().is(200))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void searchByModelWithToken() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        Page<Car> carPage = new PageImpl<Car>(cars.subList(0,1),Pageable.unpaged(),cars.size());
        // Mocks
        Mockito.when(carRepository.findCarsByModelContainingAndCarStateEquals(car.getModel(),"selling", PageRequest.of(1,1))).thenReturn(carPage);
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);

        mockMvc.perform(get("/car/model/"+car.getModel()+"?page=1&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("data")));

        MvcResult mock = mockMvc.perform(get("/car/model/"+car.getModel()+"?page=1&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void searchByModelWithoutToken() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        Page<Car> carPage = new PageImpl<Car>(cars.subList(0,1),Pageable.unpaged(),cars.size());
        // Mocks
        Mockito.when(carRepository.findCarsByModelContainingAndCarStateEquals(car.getModel(),"selling", PageRequest.of(1,1))).thenReturn(carPage);

        mockMvc.perform(get("/car/model/"+car.getModel()+"?page=1&limit=1")).andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasKey("data")));

        MvcResult mock = mockMvc.perform(get("/car/model/"+car.getModel()+"?page=1&limit=1"))
                .andExpect(status().is(200))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void searchByYearWithToken() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        Page<Car> carPage = new PageImpl<Car>(cars.subList(0,1),Pageable.unpaged(),cars.size());
        // Mocks
        Mockito.when(carRepository.findCarsByYearEqualsAndCarStateEquals(car.getYear(),"selling", PageRequest.of(1,1))).thenReturn(carPage);
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);

        mockMvc.perform(get("/car/year/"+car.getYear()+"?page=1&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("data")));

        MvcResult mock = mockMvc.perform(get("/car/year/"+car.getYear()+"?page=1&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void searchByYearWithoutToken() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        Page<Car> carPage = new PageImpl<Car>(cars.subList(0,1),Pageable.unpaged(),cars.size());
        // Mocks
        Mockito.when(carRepository.findCarsByYearEqualsAndCarStateEquals(car.getYear(),"selling", PageRequest.of(1,1))).thenReturn(carPage);

        mockMvc.perform(get("/car/year/"+car.getYear()+"?page=1&limit=1")).andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasKey("data")));

        MvcResult mock = mockMvc.perform(get("/car/year/"+car.getYear()+"?page=1&limit=1"))
                .andExpect(status().is(200))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void searchByFuelTypeWithToken() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        Page<Car> carPage = new PageImpl<Car>(cars.subList(0,1),Pageable.unpaged(),cars.size());
        // Mocks
        Mockito.when(carRepository.findCarsByTypeOfFuelEqualsAndCarStateEquals(car.getTypeOfFuel(),"selling", PageRequest.of(1,1))).thenReturn(carPage);
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);

        mockMvc.perform(get("/car/fuel/"+car.getTypeOfFuel()+"?page=1&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("data")));

        MvcResult mock = mockMvc.perform(get("/car/fuel/"+car.getTypeOfFuel()+"?page=1&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void searchByFuelTypeWithoutToken() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        Page<Car> carPage = new PageImpl<Car>(cars.subList(0,1),Pageable.unpaged(),cars.size());
        // Mocks
        Mockito.when(carRepository.findCarsByTypeOfFuelEqualsAndCarStateEquals(car.getTypeOfFuel(),"selling", PageRequest.of(1,1))).thenReturn(carPage);

        mockMvc.perform(get("/car/fuel/"+car.getTypeOfFuel()+"?page=1&limit=1")).andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasKey("data")));

        MvcResult mock = mockMvc.perform(get("/car/fuel/"+car.getTypeOfFuel()+"?page=1&limit=1"))
                .andExpect(status().is(200))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void editCarInfoWithToken()  throws Exception {
        Car optionalCar = car;
        // Mocks
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(carRepository.findCarsById(car.getId())).thenReturn(car);
        optionalCar.setYear(200200);
        Mockito.when(carRepository.save(optionalCar)).thenReturn(optionalCar);
        json.replace("year",optionalCar.getYear());

        mockMvc.perform(put("/car/"+car.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(String.valueOf(json))
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mock = mockMvc.perform(put("/car/"+car.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(String.valueOf(json))
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void editCarInfoWithInvalidToken()  throws Exception {
        Car optionalCar = car;
        // Mocks
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn("");
        Mockito.when(carRepository.findCarsById(car.getId())).thenReturn(car);
        optionalCar.setYear(200200);
        Mockito.when(carRepository.save(optionalCar)).thenReturn(optionalCar);
        json.replace("year",optionalCar.getYear());

        mockMvc.perform(put("/car/"+car.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(String.valueOf(json))
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(put("/car/"+car.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(String.valueOf(json))
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void editCarInfoWithWrongToken()  throws Exception {
        Car optionalCar = car;
        // Mocks
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn("mailinvalido@mail.com");
        Mockito.when(carRepository.findCarsById(car.getId())).thenReturn(car);
        optionalCar.setYear(200200);
        Mockito.when(carRepository.save(optionalCar)).thenReturn(optionalCar);
        json.replace("year",optionalCar.getYear());

        mockMvc.perform(put("/car/"+car.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(String.valueOf(json))
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(put("/car/"+car.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(String.valueOf(json))
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void editCarInfoWithoutToken()  throws Exception {
        Car optionalCar = car;
        // Mocks
        Mockito.when(carRepository.findCarsById(car.getId())).thenReturn(car);
        optionalCar.setYear(200200);
        Mockito.when(carRepository.save(optionalCar)).thenReturn(optionalCar);
        json.replace("year",optionalCar.getYear());
        mockMvc.perform(put("/car/"+car.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(String.valueOf(json)))
                .andDo(print())
                .andExpect(status().is(403));


        MvcResult mock = mockMvc.perform(put("/car/"+car.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(String.valueOf(json)))
                .andDo(print())
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void markCarAsSoldWithToken()  throws Exception {
        Car updateCar = car;
        // Mocks
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(carRepository.findCarsById(car.getId())).thenReturn(car);
        updateCar.setCarState("sold");
        Mockito.when(carRepository.save(updateCar)).thenReturn(updateCar);

        json.replace("carState",updateCar.getCarState());

        mockMvc.perform(put("/car/sold/"+car.getId())
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mock = mockMvc.perform(put("/car/sold/"+car.getId())
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void markCarAsSoldWithInvalidToken()  throws Exception {
        Car updateCar = car;
        // Mocks
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(null);
        Mockito.when(carRepository.findCarsById(car.getId())).thenReturn(car);
        updateCar.setCarState("sold");
        Mockito.when(carRepository.save(updateCar)).thenReturn(updateCar);

        json.replace("carState",updateCar.getCarState());

        mockMvc.perform(put("/car/sold/"+car.getId())
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(put("/car/sold/"+car.getId())
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void markCarAsSoldWithWrongToken()  throws Exception {
        Car updateCar = car;
        // Mocks
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn("mailInvalido@mail.com");
        Mockito.when(carRepository.findCarsById(car.getId())).thenReturn(car);
        updateCar.setCarState("sold");
        Mockito.when(carRepository.save(updateCar)).thenReturn(updateCar);

        json.replace("carState",updateCar.getCarState());

        mockMvc.perform(put("/car/sold/"+car.getId())
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(put("/car/sold/"+car.getId())
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void markCarAsSoldWithoutToken()  throws Exception {
        Car updateCar = car;
        // Mocks
        Mockito.when(carRepository.findCarsById(car.getId())).thenReturn(car);
        updateCar.setCarState("sold");
        Mockito.when(carRepository.save(updateCar)).thenReturn(updateCar);
        json.replace("carState",updateCar.getCarState());
        mockMvc.perform(put("/car/sold/"+car.getId()))
                .andDo(print())
                .andExpect(status().is(403));


        MvcResult mock = mockMvc.perform(put("/car/sold/"+car.getId()))
                .andDo(print())
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void getAllCarsFromVendorWithToken() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        Page<Car> carPage = new PageImpl<Car>(cars.subList(0,1),Pageable.unpaged(),cars.size());
        // Mocks
        Mockito.when(carRepository.findCarsByOwnerMail(car.getOwnerMail(), PageRequest.of(1,1))).thenReturn(carPage);
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);

        mockMvc.perform(get("/car/vendor?page=1&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("data")));

        MvcResult mock = mockMvc.perform(get("/car/vendor?page=1&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void getAllCarsFromVendorWithInvalidToken() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        Page<Car> carPage = new PageImpl<Car>(cars.subList(0,1),Pageable.unpaged(),cars.size());
        // Mocks
        Mockito.when(carRepository.findCarsByOwnerMail(car.getOwnerMail(), PageRequest.of(1,1))).thenReturn(carPage);
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn("");

        mockMvc.perform(get("/car/vendor?page=1&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(get("/car/vendor?page=1&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void getAllCarsFromVendorWithoutToken() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        Page<Car> carPage = new PageImpl<Car>(cars.subList(0,1),Pageable.unpaged(),cars.size());
        // Mocks
        Mockito.when(carRepository.findCarsByOwnerMail(car.getOwnerMail(), PageRequest.of(1,1))).thenReturn(carPage);

        mockMvc.perform(get("/car/vendor?page=1&limit=1")).andDo(print())
                .andExpect(status().is(403))
                .andExpect(jsonPath("$", hasKey("error")));

        MvcResult mock = mockMvc.perform(get("/car/vendor?page=1&limit=1"))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void getAllCarsOnSaleFromVendorWithToken() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        Page<Car> carPage = new PageImpl<Car>(cars.subList(0,1),Pageable.unpaged(),cars.size());
        // Mocks
        Mockito.when(carRepository.findCarsByOwnerMailEqualsAndAndCarStateEquals(car.getOwnerMail(),"selling", PageRequest.of(1,1))).thenReturn(carPage);
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);

        mockMvc.perform(get("/car/vendor/selling?page=1&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("data")));

        MvcResult mock = mockMvc.perform(get("/car/vendor/selling?page=1&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void getAllCarsOnSaleFromVendorWithInvalidToken() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        Page<Car> carPage = new PageImpl<Car>(cars.subList(0,1),Pageable.unpaged(),cars.size());
        // Mocks
        Mockito.when(carRepository.findCarsByOwnerMailEqualsAndAndCarStateEquals(car.getOwnerMail(),"selling", PageRequest.of(1,1))).thenReturn(carPage);
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn("");

        mockMvc.perform(get("/car/vendor/selling?page=1&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(get("/car/vendor/selling?page=1&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void getAllCarsOnSaleFromVendorWithoutToken() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        Page<Car> carPage = new PageImpl<Car>(cars.subList(0,1),Pageable.unpaged(),cars.size());
        // Mocks
        Mockito.when(carRepository.findCarsByOwnerMailEqualsAndAndCarStateEquals(car.getOwnerMail(),"selling", PageRequest.of(1,1))).thenReturn(carPage);

        mockMvc.perform(get("/car/vendor/selling?page=1&limit=1")).andDo(print())
                .andExpect(status().is(403))
                .andExpect(jsonPath("$", hasKey("error")));

        MvcResult mock = mockMvc.perform(get("/car/vendor/selling?page=1&limit=1"))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void getAllCarsSoldFromVendorWithToken() throws Exception {
        List<Car> cars = new ArrayList<>();
        car.setCarState("sold");
        cars.add(car);
        Page<Car> carPage = new PageImpl<Car>(cars.subList(0,1),Pageable.unpaged(),cars.size());
        // Mocks
        Mockito.when(carRepository.findCarsByOwnerMailEqualsAndAndCarStateEquals(car.getOwnerMail(),"sold", PageRequest.of(1,1))).thenReturn(carPage);
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);

        mockMvc.perform(get("/car/vendor/sold?page=1&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("data")));

        MvcResult mock = mockMvc.perform(get("/car/vendor/sold?page=1&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void getAllCarsSoldFromVendorWithInvalidToken() throws Exception {
        List<Car> cars = new ArrayList<>();
        car.setCarState("sold");
        cars.add(car);
        Page<Car> carPage = new PageImpl<Car>(cars.subList(0,1),Pageable.unpaged(),cars.size());
        // Mocks
        Mockito.when(carRepository.findCarsByOwnerMailEqualsAndAndCarStateEquals(car.getOwnerMail(),"sold", PageRequest.of(1,1))).thenReturn(carPage);
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn("");

        mockMvc.perform(get("/car/vendor/sold?page=1&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(get("/car/vendor/sold?page=1&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void getAllCarsSoldFromVendorWithoutToken() throws Exception {
        List<Car> cars = new ArrayList<>();
        car.setCarState("sold");
        cars.add(car);
        Page<Car> carPage = new PageImpl<Car>(cars.subList(0,1),Pageable.unpaged(),cars.size());
        // Mocks
        Mockito.when(carRepository.findCarsByOwnerMailEqualsAndAndCarStateEquals(car.getOwnerMail(),"sold", PageRequest.of(1,1))).thenReturn(carPage);

        mockMvc.perform(get("/car/vendor/sold?page=1&limit=1")).andDo(print())
                .andExpect(status().is(403))
                .andExpect(jsonPath("$", hasKey("error")));

        MvcResult mock = mockMvc.perform(get("/car/vendor/sold?page=1&limit=1"))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }
}