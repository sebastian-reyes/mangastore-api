package com.manga.api.interfaceService;

import com.manga.api.model.Volumen;

public interface IVolumenService {
    public Volumen buscarVolumen(int id);
    public Volumen guardarVolumen(Volumen v);
    public Volumen findByUrl(String url);
}
