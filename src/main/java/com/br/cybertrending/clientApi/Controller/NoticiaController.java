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

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id){
        noticiaService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
