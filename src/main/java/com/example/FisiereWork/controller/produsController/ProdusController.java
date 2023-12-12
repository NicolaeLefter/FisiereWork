package com.example.FisiereWork.controller.produsController;

import com.example.FisiereWork.repository.produsRepository.ProdusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/produs")
public class ProdusController {
    @Autowired
    ProdusRepository produsRepository;

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(produsRepository.findAll());
    }
}
