package com.manga.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manga.api.interfaceService.IAutorService;
import com.manga.api.model.Autor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mangaka")
public class AutorRestController {

    @Autowired
    private IAutorService service;

    @GetMapping
    public ResponseEntity<?> listarAutores() {
        Map<String, Object> response = new HashMap<>();
        List<Autor> lstAutores = service.listarAutores();
        response.put("autores", lstAutores);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarAutor(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        Autor autor = null;
        try {
            autor = service.buscarAutor(id);
            if(autor != null){
                return new ResponseEntity<>(autor, HttpStatus.OK);
            }else{
                response.put("mensaje", "No se encontró este mangaka en la base de datos.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta a la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
