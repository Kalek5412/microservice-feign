package com.example.car.service.service;

import com.example.car.service.entity.Car;
import com.example.car.service.reposiroty.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> getAll(){
        return carRepository.findAll();
    }

    public Car getCarId(Integer id){
        return carRepository.findById(id).orElse(null);
    }

    public Car saveCar(Car Car){
        Car carro = carRepository.save(Car);
        return carro;
    }

    public List<Car> byUserId(Integer userId){
        return carRepository.findByUserId(userId);
    }

}
