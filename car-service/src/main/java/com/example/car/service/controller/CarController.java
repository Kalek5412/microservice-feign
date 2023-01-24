package com.example.car.service.controller;

import com.example.car.service.entity.Car;
import com.example.car.service.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getAll(){
        List<Car> users=carService.getAll();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarId(@PathVariable Integer id){
        Car userOp=carService.getCarId(id);
        if(userOp==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userOp);
    }

    @PostMapping
    public ResponseEntity<?> saveCar(@RequestBody Car car){
        Car user=carService.saveCar(car);
       return  ResponseEntity.ok(user);
    }

    @GetMapping("/byUser/{idUser}")
    public ResponseEntity<?> getByUserId(@PathVariable Integer idUser){
        List<Car> users=carService.byUserId(idUser);
        return ResponseEntity.ok(users);
    }
}

