package com.bootcamp.conta.service.config;

import com.bootcamp.conta.service.exceptions.ContaExistenteException;
import com.bootcamp.conta.service.exceptions.ContaNaoExisteException;
import com.bootcamp.conta.service.exceptions.ErroCadastroChaveBacenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ContaExistenteException.class)
    private ProblemDetail exceptionContaExistente(ContaExistenteException e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problemDetail.setTitle("Conflict");
        problemDetail.setType(URI.create("http://localhost/9000/doc/conta-existente"));
        return problemDetail;
    }
    @ExceptionHandler(ContaNaoExisteException.class)
    private ProblemDetail excepContaNaoExiste(ContaNaoExisteException e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Not Found");
        problemDetail.setType(URI.create("http://localhost/9000/doc/conta-nao-existe"));
        return problemDetail;
    }
    @ExceptionHandler(ErroCadastroChaveBacenException.class)
    private ProblemDetail exceptionErroCadastroChaveBacenException(ErroCadastroChaveBacenException e){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Error Bacen");
        problemDetail.setType(URI.create("http://localhost/9000/doc/erro-cadastro-chave-bacen"));
        return problemDetail;
    }
}
