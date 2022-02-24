package com.manga.api.repository;

import com.manga.api.model.Manga;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MangaRepository extends JpaRepository<Manga, Integer>{
    
}
