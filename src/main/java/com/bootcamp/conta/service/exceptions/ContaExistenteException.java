package com.bootcamp.conta.service.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ContaExistenteException extends RuntimeException{

    public ContaExistenteException(String message){
        super(message);
    }
}
