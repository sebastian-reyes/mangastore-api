package com.manga.api.interfaceService;

import java.util.List;

import com.manga.api.model.Autor;

public interface IAutorService {
    public List<Autor> listarAutores();
    public Autor buscarAutor(int id);
    public Autor guardarAutor(Autor a);
}
