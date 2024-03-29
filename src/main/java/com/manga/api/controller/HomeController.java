package com.manga.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<?> home(){
        Map<String, Object> response = new HashMap<>();
        response.put("Mangakas", "https://test-apimanga.herokuapp.com/api/mangaka");
        response.put("Mangas", "https://test-apimanga.herokuapp.com/api/mangas/page/0");
        response.put("Volumenes", "https://test-apimanga.herokuapp.com/api/volumen/1");
        response.put("Fotos de volumenes", "https://test-apimanga.herokuapp.com/api/volumen/foto/1");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
