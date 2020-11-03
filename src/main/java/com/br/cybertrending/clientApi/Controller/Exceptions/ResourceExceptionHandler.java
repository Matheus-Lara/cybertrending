package com.br.cybertrending.clientApi.Controller.Exceptions;

import com.br.cybertrending.clientApi.Controller.Exceptions.type.DataIntegrationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

//Cria um tratamento de exceção unificado em toda o aplicação, ou seja
// todas as exceptions lançadas irão passar por esse controller.
@ControllerAdvice
public class ResourceExceptionHandler {

    //Metodo para tratar as exeptions lançadas do tipo DataIntegrationException
    @ExceptionHandler(DataIntegrationException.class)

    public ResponseEntity<StandardError> dataError(DataIntegrationException e, HttpServletRequest request) {

        // Monta um objeto do tipo StandardError informando os dados da Exception
        StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());

        //Envia uma resposta de erro ao usuário contendo uma mensagem de erro
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}