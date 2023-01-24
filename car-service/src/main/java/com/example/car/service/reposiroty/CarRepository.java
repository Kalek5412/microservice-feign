package com.example.car.service.reposiroty;

import com.example.car.service.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Integer> {
    public List<Car> findByUserId(Integer userId);
}
