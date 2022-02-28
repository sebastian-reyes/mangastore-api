package com.manga.api.service;

import com.manga.api.interfaceService.IVolumenService;
import com.manga.api.model.Volumen;
import com.manga.api.repository.VolumenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VolumenService implements IVolumenService{

    @Autowired
    private VolumenRepository repository;

    @Override
    public Volumen buscarVolumen(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Volumen guardarVolumen(Volumen v) {
        return repository.save(v);
    }
    
}
