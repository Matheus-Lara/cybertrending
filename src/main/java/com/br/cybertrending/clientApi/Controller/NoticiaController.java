package com.br.cybertrending.clientApi.Controller;

import com.br.cybertrending.clientApi.Dto.NoticiaDto;
import com.br.cybertrending.clientApi.Entity.Noticia;
import com.br.cybertrending.clientApi.Service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController

//habilita utilização de CORS
@CrossOrigin

//Mapea a rota pai Notícia
@RequestMapping(value = "/noticia")

public class NoticiaController {

    @Autowired
    NoticiaService noticiaService; //referência à camada Service de Noticia

    //Mapeando rota que retorna todas as noticias
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<NoticiaDto>> getAll(){

        var obj = noticiaService.findAll()
                .stream()
                .map(Noticia::noticiaDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(obj);
    }

    //Mapeia a rota e retorna o get de uma noticia específica com base no seu ID
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<NoticiaDto> get(@PathVariable int id){
        var obj = noticiaService.find(id).noticiaDto();
        //retorna os dados
        return ResponseEntity.ok(obj);
    }

    //Mapeia a rota e aciona a rotina para gravar em banco uma nova noticia
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<NoticiaDto> post(@Valid @RequestBody NoticiaDto objDTO) {
        noticiaService.insert(objDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //Mapeia a rota e aciona a rotina para gravar upvotes
    @RequestMapping(value = "/{id}/upvote", method = RequestMethod.PUT)
    public ResponseEntity<NoticiaDto> updateUpvote(@PathVariable int id) {
        var objNoticia = noticiaService.find(id);
        objNoticia.setUpQtd((objNoticia.getUpQtd()+1));
        noticiaService.update(id, objNoticia);
        return ResponseEntity.ok(objNoticia.noticiaDto());
    }

    //Mapeia a rota e aciona a rotina para gravar upvotes
    @RequestMapping(value = "/{id}/downvote", method = RequestMethod.PUT)
    public ResponseEntity<NoticiaDto> updateDownvote(@PathVariable int id) {
        var objNoticia = noticiaService.find(id);
        objNoticia.setDownQtd((objNoticia.getDownQtd()+1));

        noticiaService.update(id, objNoticia);
        return ResponseEntity.ok(objNoticia.noticiaDto());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id){

        noticiaService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
