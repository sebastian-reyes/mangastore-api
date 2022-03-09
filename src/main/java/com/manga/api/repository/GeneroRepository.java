package com.manga.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manga.api.model.Genero;

public interface GeneroRepository extends JpaRepository<Genero, Integer>{
	public Genero findByNombre(String nombre);
}
