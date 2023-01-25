package com.user.service.service;

import com.user.service.entity.Usuario;
import com.user.service.feignClient.CarFeignClient;
import com.user.service.feignClient.MotoFeignClient;
import com.user.service.models.Car;
import com.user.service.models.Moto;
import com.user.service.reposiroty.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CarFeignClient carFeignClient;

    @Autowired
    private MotoFeignClient motoFeignClient;

    public List<Usuario> getAll(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioId(Integer id){
        return usuarioRepository.findById(id);
    }

    public Usuario saveUsuario(Usuario usuario){
        Usuario user = usuarioRepository.save(usuario);
        return user;
    }

    public List<Car> getCars(Integer userId){
        List<Car> cars= restTemplate.getForObject("http://car-service/car/byUser/"+ userId,List.class);
        return  cars;
    }
    public List<Moto> getMotos(Integer userId){
        List<Moto> motos= restTemplate.getForObject("http://moto-service/moto/byUser/"+ userId,List.class);
        return  motos;
    }

    public Car saveCar(Integer userId, Car car){
        car.setUserId(userId);
        Car carFeign=carFeignClient.save(car);
        return carFeign;
    }

    public Moto saveMoto(Integer userId, Moto moto){
        moto.setUserId(userId);
        Moto motoFeign = motoFeignClient.saveMoto(moto);
        return motoFeign;
    }

    public Map<String, Object> getUserAndVehicles(Integer userId){
        Map<String,Object> result= new HashMap<>();
        Usuario usuario = usuarioRepository.findById(userId).orElse(null);
        if(usuario==null){
            result.put("Mensaje","no existe el usuario");
            return result;
        }
        result.put("Usuario",usuario);

        List<Car> cars=carFeignClient.getByUserId(userId);
        if(cars.isEmpty()){
            result.put("Cars","el usuario no tiene noches");
        }else{
            result.put("Cars",cars);
        }
        List<Moto> motos= motoFeignClient.getByUserId(userId);
        if(motos.isEmpty()){
            result.put("Motos","el usuario no tiene motos");
        }else{
            result.put("Motos",motos);
        }
        return  result;
    }

}
