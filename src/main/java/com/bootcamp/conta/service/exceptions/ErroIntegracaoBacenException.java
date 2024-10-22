package com.bootcamp.conta.service.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ErroIntegracaoBacenException extends RuntimeException{
     public ErroIntegracaoBacenException(String message, Throwable cause){
         super(message, cause);
     }
}
