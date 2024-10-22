package com.bootcamp.conta.service.controller;


import com.bootcamp.conta.service.dto.ContaDTO;
import com.bootcamp.conta.service.dto.ContaRequestDTO;
import com.bootcamp.conta.service.dto.ContaResponseDTO;
import com.bootcamp.conta.service.service.ContaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


//http://localhost:9000/h2-console , daí coloquei o JDB URL como: jdbc:h2:file:./data/conta_service


//Base da API, coloquei no POSTMAN a URL como: http://localhost:9000/api/contas
@RequestMapping("/api/contas")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ContaController {

    private final ContaService contaService;

    @PostMapping
    public ResponseEntity<ContaResponseDTO> cadastroConta(@RequestBody @Valid ContaRequestDTO contaRequestDTO)throws Exception{

        ContaResponseDTO contaResponseDTO = contaService.criarConta(contaRequestDTO);
        return new ResponseEntity<>(contaResponseDTO, HttpStatus.CREATED);
    }

    /*
        ResponseEntity: colocar o status code correto, que seria o 201
        Se nao colocarmos explicitamente o codigo, sera retornado por padrao somente 200
    */

    @GetMapping
    public ResponseEntity<List<ContaDTO>> consultaContas (){

        return ResponseEntity.status(HttpStatus.OK).body(contaService.consultaContas());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConta(@PathVariable UUID id){

        contaService.deletarConta(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaResponseDTO> atualizarConta(@RequestBody @Valid ContaRequestDTO contaRequestDTO, @PathVariable UUID id)throws Exception{

        ContaResponseDTO contaResponseDTO = contaService.atualizarConta(contaRequestDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(contaResponseDTO);
    }



    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> consultaContaPorID(@PathVariable UUID id) throws Exception{

        return ResponseEntity.status(HttpStatus.OK).body(contaService.consultaContaPorID(id));
    }

}
