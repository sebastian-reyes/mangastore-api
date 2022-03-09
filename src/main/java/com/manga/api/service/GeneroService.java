package com.manga.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manga.api.interfaceService.IGeneroService;
import com.manga.api.model.Genero;
import com.manga.api.repository.GeneroRepository;

@Service
public class GeneroService implements IGeneroService{

	@Autowired
	private GeneroRepository repository;
	
	@Override
	public Genero findByNombre(String nombre_genero) {
		return repository.findByNombre(nombre_genero);
	}

}
