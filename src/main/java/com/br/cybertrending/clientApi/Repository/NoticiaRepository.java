package com.br.cybertrending.clientApi.Repository;

import com.br.cybertrending.clientApi.Entity.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//A annotation Informa que a uma interface de um repositório
@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Integer> {

    Optional<Noticia> findOptionalByTagTwitter(String tagTwitter);

}