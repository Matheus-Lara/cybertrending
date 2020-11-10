package com.br.cybertrending.clientApi.Service;

import com.br.cybertrending.clientApi.Dto.NoticiaDto;
import com.br.cybertrending.clientApi.Entity.Noticia;

import java.util.List;
import java.util.Optional;

public interface NoticiaService {
    Noticia find(int id);
    List<Noticia> findAll();
    Noticia update(int idNoticia, Noticia noticia);
    Noticia insert(NoticiaDto noticia);
    void delete(int idNoticia);
}