package com.manga.api.controller;

import java.util.HashMap;
import java.util.Map;

import com.manga.api.interfaceService.IMangaService;
import com.manga.api.model.Manga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mangas")
public class MangaRestController {

    @Autowired
    private IMangaService service;

    @GetMapping("/page/{page}")
    public Page<Manga> listarMangas(@PathVariable Integer page) {
        return service.listarMangas(PageRequest.of(page, 10));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarManga(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        Manga manga = null;
        try {
            manga = service.buscarManga(id);
            if (manga != null) {
                return new ResponseEntity<>(manga, HttpStatus.OK);
            } else {
                response.put("mensaje", "El manga no se encontró en la base de datos.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta a la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
