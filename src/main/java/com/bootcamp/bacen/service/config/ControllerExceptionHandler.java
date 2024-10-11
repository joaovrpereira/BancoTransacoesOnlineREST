package com.bootcamp.bacen.service.config;


import com.bootcamp.bacen.service.exception.ChaveJaCadastradaException;
import com.bootcamp.bacen.service.exception.ChaveNaoLocalizadaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ChaveJaCadastradaException.class)
    private ProblemDetail handlerChaveJaCadastrada(ChaveJaCadastradaException exc){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exc.getMessage());
        problemDetail.setTitle("Chave duplicada");
        problemDetail.setType(URI.create("http://localhost/9001/documents/chave-duplicada"));
        return problemDetail;
    }

    @ExceptionHandler(ChaveNaoLocalizadaException.class)
    private ProblemDetail handlerChaveNaoLocalizada(ChaveNaoLocalizadaException exc){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exc.getMessage());
        problemDetail.setTitle("Chave não localizada");
        problemDetail.setType(URI.create("http://localhost/9001/documents/chave-nao-localizada"));
        return problemDetail;
    }


}
