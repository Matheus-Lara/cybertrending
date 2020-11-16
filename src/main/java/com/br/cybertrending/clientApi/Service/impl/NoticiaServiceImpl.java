package com.br.cybertrending.clientApi.Service.impl;

import com.br.cybertrending.clientApi.Controller.Exceptions.type.DataIntegrationException;
import com.br.cybertrending.clientApi.Dto.NoticiaDto;
import com.br.cybertrending.clientApi.Entity.Noticia;
import com.br.cybertrending.clientApi.Repository.NoticiaIpRepository;
import com.br.cybertrending.clientApi.Repository.NoticiaRepository;
import com.br.cybertrending.clientApi.Service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticiaServiceImpl implements NoticiaService {


    @Autowired
    NoticiaRepository noticiaRepository;

    public Noticia find(int id){
        var objNoticia = noticiaRepository.findById(id);

        if(!objNoticia.isPresent())
            throw new DataIntegrationException("Esta Notícia Não Existe!");

        return objNoticia.get();
    }


    public List<Noticia> findAll(){
        return noticiaRepository.findAll();
    }


    public Noticia update(int idNoticia, Noticia noticia){
        var objNoticia = this.find(idNoticia);
        objNoticia.setUpQtd(noticia.getUpQtd());
        objNoticia.setDownQtd(noticia.getDownQtd());
        return noticiaRepository.save(objNoticia);
    }


    public Noticia insert(NoticiaDto noticia) {

        var objNoticia = noticiaRepository.findOptionalByUrlNoticia(noticia.getUrlNoticia());

        if(objNoticia.isPresent()){
            throw new DataIntegrationException("Esta notícia já existe");
        }

        return noticiaRepository.save(Noticia.builder()
                .dataInclusao(noticia.getDataInclusao())
                .dataFinal(noticia.getDataFinal())
                .tagTwitter(noticia.getTagTwitter())
                .urlTagTwitter(noticia.getUrlTagTwitter())
                .urlNoticia(noticia.getUrlNoticia())
                .tituloNoticia(noticia.getTituloNoticia())
                .conteudoNoticia(noticia.getConteudoNoticia())
                .upQtd(noticia.getUpQtd())
                .downQtd(noticia.getDownQtd())
                .build()
        );
    }


    public void delete(int idNoticia) {
        var objNoticia = this.find(idNoticia);
        noticiaRepository.delete(objNoticia);
    }

}
