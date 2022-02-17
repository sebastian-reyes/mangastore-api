package com.manga.api.repository;

import com.manga.api.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    public Usuario findByEmail(String email);
}
