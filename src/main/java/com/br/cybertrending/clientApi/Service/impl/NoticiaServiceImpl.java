package com.br.cybertrending.clientApi.Service.impl;

//import lombok.var;
import com.br.cybertrending.clientApi.Controller.Exceptions.type.DataIntegrationException;
import com.br.cybertrending.clientApi.Dto.NoticiaDto;
import com.br.cybertrending.clientApi.Entity.Noticia;
import com.br.cybertrending.clientApi.Repository.NoticiaRepository;
import com.br.cybertrending.clientApi.Service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//Informa que a classe é um service e implementa o serviço de noticias
@Service
public class NoticiaServiceImpl implements NoticiaService {

    // instancia um objeto do tipo NoticiaRepository que possibilita a manipulação de dados do database
    @Autowired
    NoticiaRepository noticiaRepository;

    //busca a notícia pelo ID --> c(R)ud
    public Noticia find(int id){

        var objNoticia = noticiaRepository.findById(id);

        //verifica se a notícia não existe
        if(!objNoticia.isPresent()) {
            //lança a exceção no modelo Genérico criado nas controllers
            throw new DataIntegrationException("Esta Notícia Não Existe!");
        }

        return objNoticia.get();
    }

    //busca todas as noticias do database --> c(R)ud
    public List<Noticia> findAll(){
        return noticiaRepository.findAll();
    }

    //executa update da noticia (para DownVotes e upVotes) --> cr(U)d
    public Noticia update(int idNoticia, Noticia noticia){
        //faz a busca da notícia a alterar
        var objNoticia = this.find(idNoticia);

        //sobescreve os valores de upvote e downvote
        objNoticia.setUpQtd(noticia.getUpQtd());
        objNoticia.setDownQtd(noticia.getDownQtd());

        //atualiza os dados
        return noticiaRepository.save(objNoticia);
    }

    // Grava uma nova noticia no banco
    public Noticia insert(NoticiaDto noticia) {
        var objNoticia = noticiaRepository.findOptionalByTagTwitter(noticia.getTagTwitter());

        //Verifica se já existe uma noticia com a Tag informada
        if(objNoticia.isPresent()){
            //Caso exista retorna um JSON com exception de integração de dados onde a tag ja existe
            throw new DataIntegrationException("Esta tag já possui notícia!");
        }

        //salva os dados setando via builder
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

    //Deleta uma noticia do banco de dados
    public void delete(int idNoticia) {
        //busca o objeto da noticia que será deletada
        var objNoticia = this.find(idNoticia);
        //deleta do banco
        noticiaRepository.delete(objNoticia);
    }
}
