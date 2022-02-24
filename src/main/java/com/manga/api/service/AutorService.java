package com.manga.api.service;

import java.util.List;

import com.manga.api.interfaceService.IAutorService;
import com.manga.api.model.Autor;
import com.manga.api.repository.AutorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorService implements IAutorService{
    
    @Autowired
    private AutorRepository repository;

    @Override
    public List<Autor> listarAutores() {
        return (List<Autor>)repository.findAll();
    }

    @Override
    public Autor buscarAutor(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Autor guardarAutor(Autor a) {
        return repository.save(a);
    }
}
