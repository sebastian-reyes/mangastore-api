package com.manga.api.controller;

import com.manga.api.interfaceService.IMangaService;
import com.manga.api.model.Manga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Page<Manga> listarMangas(@PathVariable Integer page){
        return service.listarMangas(PageRequest.of(page,10));
    }
}
