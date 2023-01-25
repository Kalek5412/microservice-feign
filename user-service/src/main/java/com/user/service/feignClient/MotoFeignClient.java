package com.user.service.feignClient;

import com.user.service.models.Moto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "moto-service")
public interface MotoFeignClient {
    @PostMapping
    Moto saveMoto(@RequestBody Moto moto);


    @GetMapping("/byUser/{idUser}")
    List<Moto> getByUserId(@PathVariable Integer idUser);
}
