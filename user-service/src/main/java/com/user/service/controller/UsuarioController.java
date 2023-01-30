package com.user.service.controller;

import com.user.service.entity.Usuario;
import com.user.service.models.Car;
import com.user.service.models.Moto;
import com.user.service.service.UsuarioService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll(){
        List<Usuario> users=usuarioService.getAll();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioId(@PathVariable Integer id){
        Optional<Usuario> userOp=usuarioService.getUsuarioId(id);
        if(!userOp.isPresent()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userOp.get());
    }
    @PostMapping
    public ResponseEntity<?> saveUsuario(@RequestBody Usuario usuario){
        Usuario user=usuarioService.saveUsuario(usuario);
       return  ResponseEntity.ok(user);
    }
    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable Integer userId){
        Optional<Usuario> usuario=usuarioService.getUsuarioId(userId);
        if(usuario==null){
            return ResponseEntity.notFound().build();
        }
        List<Car> cars=usuarioService.getCars(userId);
        return ResponseEntity.ok(cars);
    }
    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCarFeign")
    @PostMapping("/savecar/{userId}")
    public ResponseEntity<Car> saveCarFeign(@PathVariable("userId") Integer userId,@RequestBody Car car){
        if(usuarioService.getUsuarioId(userId)==null){
            return ResponseEntity.notFound().build();
        }
        Car carFeign = usuarioService.saveCar(userId,car);
        return ResponseEntity.ok(carFeign);
    }
    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackGetMotos")
    @GetMapping("/motos/{userId}")
    public ResponseEntity<List<Moto>> getMotos(@PathVariable Integer userId){
        Optional<Usuario> usuario=usuarioService.getUsuarioId(userId);
        if(usuario==null){
            return ResponseEntity.notFound().build();
        }
        List<Moto> motos=usuarioService.getMotos(userId);
        return ResponseEntity.ok(motos);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackSaveMotoFeign")
    @PostMapping("/savemoto/{userId}")
    public ResponseEntity<Moto> saveMotoFeign(@PathVariable("userId") Integer userId,@RequestBody Moto moto){
        if(usuarioService.getUsuarioId(userId)==null){
            return ResponseEntity.notFound().build();
        }
        Moto motoFeign=usuarioService.saveMoto(userId,moto);
        return ResponseEntity.ok(motoFeign);
    }
    @CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetAll")
    @GetMapping("/getall/{userId}")
    public ResponseEntity<Map<String,Object>> getAllVehicles(@PathVariable("userId") Integer userId){
        Map<String,Object> result= usuarioService.getUserAndVehicles(userId);
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<List<Car>> fallBackGetCars(@PathVariable Integer userId, RuntimeException e){
        return new ResponseEntity("user: "+userId+" tienes los coches en el taller", HttpStatus.OK);
    }
    private ResponseEntity<Car> fallBackSaveCarFeign(@PathVariable Integer userId,@RequestBody Car car,RuntimeException e){
        return new ResponseEntity("user: "+userId+" no tiene dinero para el auto", HttpStatus.OK);
    }
    private ResponseEntity<List<Moto>> fallBackGetMotos(@PathVariable Integer userId, RuntimeException e){
        return new ResponseEntity("user: "+userId+" tienes las motos en el taller", HttpStatus.OK);
    }
    private ResponseEntity<Moto> fallBackSaveMotoFeign(@PathVariable Integer userId,@RequestBody Moto moto,RuntimeException e){
        return new ResponseEntity("user: "+userId+" no tiene dinero para moto", HttpStatus.OK);
    }
    private ResponseEntity<Map<String,Object>> fallBackGetAll(@PathVariable Integer userId, RuntimeException e){
        return new ResponseEntity("user: "+userId+" tiene los vehiculos en el taller", HttpStatus.OK);
    }
}

