package com.apap.tutorial7.controller;

import com.apap.tutorial7.model.*;
import com.apap.tutorial7.rest.Setting;
import com.apap.tutorial7.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    private DealerService dealerService;

    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate restT(){
        return new RestTemplate();
    }

    @GetMapping(value = "/model")
    private Object getCarModel(@RequestParam("factory") String factory) throws Exception{
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent","Mozilla/5,0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Mobile Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        String path = Setting.carUrl + factory + "&year=" + year;
        Object response = restTemplate.exchange(path, HttpMethod.GET, entity, Object.class);

        System.out.println(path);
        return response;
    }

    @PostMapping(value = "/add")
    private CarModel addCarSubmit(@RequestBody CarModel car) {
        return carService.addCar(car);
    }

    @GetMapping(value = "/{carId}")
    private CarModel viewCar(@PathVariable("carId") long carId, Model model){
        CarModel car = carService.getCarById(carId).get();
        car.setDealer(null);
        return car;
    }

    @DeleteMapping(value= "/delete")
    private String deleteCar(@RequestParam("carId") long id, Model model){
        CarModel car = carService.getCarById(id).get();
        carService.deleteCar(car);
        return "delete-success";
    }

    @PutMapping(value="/{id}")
    private String updateCarSubmit(
            @PathVariable(value="id") long id,
            @RequestParam("brand") String brand,
            @RequestParam("type") String type,
            @RequestParam("price") long price,
            @RequestParam("amount") Integer amount,
            @RequestParam("dealerId") long dealerId){
        DealerModel dealer = dealerService.getDealerDetailById(id).get();
        CarModel car = carService.getCarById(id).get();
        if (dealer.equals(null)){
            return "no-dealer";
        }
        if (car.equals(null)){
            return "no-car";
        }
        car.setDealer(dealer);
        car.setAmount(amount);
        car.setBrand(brand);
        car.setPrice(price);
        car.setType(type);
        carService.addCar(car);
        return "update-success";
    }

    @GetMapping()
    private List<CarModel> viewAllCar(Model model){
        List<CarModel> lstCar = carService.getAllCar();
        for (CarModel car : lstCar){
            car.setDealer(null);
        }
        return lstCar;
    }

}
