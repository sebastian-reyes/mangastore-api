package com.manga.api.interfaceService;

import com.manga.api.model.Manga;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMangaService {
    public Page<Manga> listarMangas(Pageable pageable);
    public Manga guardarManga(Manga m);
    public Manga buscarManga(int id);
    public Manga findByUrl(String url);
}
