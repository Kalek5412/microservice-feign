package com.user.service.feignClient;

import com.user.service.models.Car;
import com.user.service.models.Moto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "car-service", url = "http://localhost:8002/car")
public interface CarFeignClient {
    @PostMapping
    Car save(@RequestBody Car car);

    @GetMapping("/byUser/{idUser}")
    List<Car> getByUserId(@PathVariable Integer idUser);



}
