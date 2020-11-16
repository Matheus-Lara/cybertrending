package com.br.cybertrending.clientApi.Repository;

import com.br.cybertrending.clientApi.Entity.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Integer> {
    Optional<Noticia> findOptionalByUrlNoticia(String urlNoticia);
}