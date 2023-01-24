package com.example.motoservice.service;

import com.example.motoservice.entity.Moto;
import com.example.motoservice.reposiroty.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    public List<Moto> getAll(){
        return motoRepository.findAll();
    }

    public Moto getMotoId(Integer id){
        return motoRepository.findById(id).orElse(null);
    }

    public Moto saveMoto(Moto moto){
        Moto user = motoRepository.save(moto);
        return user;
    }

    public List<Moto> byUserId(Integer userId){
        return motoRepository.findByUserId(userId);
    }

}
