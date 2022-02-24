package com.manga.api.repository;

import com.manga.api.model.Autor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Integer>{
    
}
