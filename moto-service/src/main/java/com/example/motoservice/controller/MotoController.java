package com.example.motoservice.controller;

import com.example.motoservice.entity.Moto;
import com.example.motoservice.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/moto")
public class MotoController {
    @Autowired
    private MotoService motoService;

    @GetMapping
    public ResponseEntity<List<Moto>> getAll() {
        List<Moto> users = motoService.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMotoId(@PathVariable Integer id) {
        Moto userOp = motoService.getMotoId(id);
        if (userOp==null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userOp);
    }

    @PostMapping
    public ResponseEntity<?> saveMoto(@RequestBody Moto moto) {
        Moto user = motoService.saveMoto(moto);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/byUser/{idUser}")
    public ResponseEntity<?> getByUserId(@PathVariable Integer idUser) {
        List<Moto> users = motoService.byUserId(idUser);
        return ResponseEntity.ok(users);
    }
}

