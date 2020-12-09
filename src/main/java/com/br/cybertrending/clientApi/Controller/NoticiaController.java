package com.br.cybertrending.clientApi.Controller;

import com.br.cybertrending.clientApi.Dto.NoticiaDto;
import com.br.cybertrending.clientApi.Entity.Noticia;
import com.br.cybertrending.clientApi.Entity.NoticiaIp;
import com.br.cybertrending.clientApi.Repository.NoticiaIpRepository;
import com.br.cybertrending.clientApi.Service.NoticiaService;
import com.br.cybertrending.clientApi.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/noticia")

public class NoticiaController {

    @Autowired NoticiaService noticiaService;

    @Autowired NoticiaIpRepository noticiaIpRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<NoticiaDto>> getAll(){
        var obj = noticiaService.findAll()
                .stream()
                .map(Noticia::noticiaDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(obj);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<NoticiaDto> get(@PathVariable int id){
        var obj = noticiaService.find(id).noticiaDto();
        return ResponseEntity.ok(obj);
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<NoticiaDto> post(@Valid @RequestBody NoticiaDto objDTO) {
        noticiaService.insert(objDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @RequestMapping(value = "/{id}/upvote", method = RequestMethod.PUT)
    public ResponseEntity<NoticiaDto> updateUpvote(HttpServletRequest request, @PathVariable int id) {

        var objNoticia = this.updateVoteByType(noticiaService.find(id), Util.getClientIpAddress(request) , "U");
        return ResponseEntity.ok(objNoticia.noticiaDto());
    }


    @RequestMapping(value = "/{id}/downvote", method = RequestMethod.PUT)
    public ResponseEntity<NoticiaDto> updateDownvote(HttpServletRequest request, @PathVariable int id) {

        var objNoticia = this.updateVoteByType(noticiaService.find(id), Util.getClientIpAddress(request) ,"D");
        return ResponseEntity.ok(objNoticia.noticiaDto());
    }
    

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id){
        noticiaService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }





    //retorna objeto atualizado validado pelo IP do usuário --> paliativo
    private Noticia updateVoteByType(Noticia noticia, String userIp, String voteType){
        var optIp = noticiaIpRepository.findOptionalByIdNoticiaAndUserIp(noticia.getId(), userIp);

        NoticiaIp objNoticiaIp = null;

        if(!optIp.isPresent()){
            objNoticiaIp = new NoticiaIp();
            objNoticiaIp.setUserIp(userIp);
            objNoticiaIp.setVoteType(voteType);
            objNoticiaIp.setIdNoticia(noticia.getId());

            if(voteType.equals("U")) noticia.setUpQtd(noticia.getUpQtd()+1);
            else noticia.setDownQtd(noticia.getDownQtd()+1);

        } else {
            var igual = false;

            objNoticiaIp = noticiaIpRepository.findByUserIpAndIdNoticia(userIp, noticia.getId());

            if(objNoticiaIp.getVoteType().equals(voteType)) igual = true;

            if(igual){
                objNoticiaIp.setVoteType("");

                if(voteType.equals("U")) noticia.setUpQtd(noticia.getUpQtd()-1);
                else noticia.setDownQtd(noticia.getDownQtd()-1);

            } else {
                switch(voteType){
                    case "U":
                        if(objNoticiaIp.getVoteType().equals("D"))
                            noticia.setDownQtd(noticia.getDownQtd()-1);
                        noticia.setUpQtd(noticia.getUpQtd()+1);
                    break;
                    case "D":
                        if(objNoticiaIp.getVoteType().equals("U"))
                            noticia.setUpQtd(noticia.getUpQtd()-1);
                        noticia.setDownQtd(noticia.getDownQtd()+1);
                    break;
                    default:

                }
                objNoticiaIp.setVoteType(voteType);
            }
        }
        noticiaIpRepository.save(objNoticiaIp);

        return noticiaService.update(noticia.getId(), noticia);
    }
}
