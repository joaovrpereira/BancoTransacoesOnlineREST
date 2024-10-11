package com.bootcamp.conta.service.controller;


import com.bootcamp.conta.service.dto.ContaDTO;
import com.bootcamp.conta.service.dto.ContaRequestDTO;
import com.bootcamp.conta.service.dto.ContaResponseDTO;
import com.bootcamp.conta.service.exceptions.ContaExistenteException;
import com.bootcamp.conta.service.exceptions.ContaNaoExisteException;
import com.bootcamp.conta.service.model.Conta;
import com.bootcamp.conta.service.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


//http://localhost:9000/h2-console , daí coloquei o JDB URL como: jdbc:h2:file:./data/conta_service


//Base da API, coloquei no POSTMAN a URL como: http://localhost:9000/api/contas
@RequestMapping("/api/contas")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ContaController {

    private final ContaRepository contaRepository;

    @PostMapping
    public ResponseEntity<ContaResponseDTO> cadastroConta(@RequestBody ContaRequestDTO contaRequestDTO)throws Exception{

        Optional<Conta> contaOptional = contaRepository.findByNomeTitularAndNumeroContaAndChavePix(
                contaRequestDTO.getNomeTitular(),
                contaRequestDTO.getNumeroConta(),
                contaRequestDTO.getChavePix()
        );

        if(contaOptional.isPresent()){
            throw new ContaExistenteException("Conta já existe.");
        }


        //Esqueci de colocar o saldo no Builder.
        // Apesar de ser um valor que nao eh passado no ContaRequestDTO, precisamos colocar com valor BigDecimal.ZERO

        Conta conta = Conta.builder()
                .nomeTitular(contaRequestDTO.getNomeTitular())
                .numeroAgencia(contaRequestDTO.getNumeroAgencia())
                .numeroConta(contaRequestDTO.getNumeroConta())
                .chavePix(contaRequestDTO.getChavePix())
                .saldo(BigDecimal.ZERO).build();



        //Salva no banco, e entao retornamos
        Conta contaSalva = contaRepository.save(conta);

        ContaResponseDTO contaResponseDTO = ContaResponseDTO.builder()
                .id(conta.getId())
                .nomeTitular(contaRequestDTO.getNomeTitular())
                .build();

        log.info("ContaResponse: {}", contaResponseDTO);

        //Retorna o HTTP status code quando CREATED (criado com sucesso) ou seja, 201
        return new ResponseEntity<>(contaResponseDTO, HttpStatus.CREATED);
    }

    /*
        ResponseEntity: colocar o status code correto, que seria o 201

        Se nao colocarmos explicitamente o codigo, sera retornado por padrao somente 200
     */


    @GetMapping
    public ResponseEntity<List<ContaDTO>> consultaContas (){

        //Solucionando o problema de buscar a tabela de historicoPix todas as vezes
        List<ContaDTO> contas = contaRepository.findAll().stream().map(
                conta -> ContaDTO.builder()
                        .id(conta.getId())
                        .nomeTitular(conta.getNomeTitular())
                        .numeroAgencia(conta.getNumeroAgencia())
                        .numeroConta(conta.getNumeroConta())
                        .chavePix(conta.getChavePix())
                        .saldo(conta.getSaldo())
                        .build()).toList();

        return ResponseEntity.status(HttpStatus.OK).body(contas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConta(@PathVariable UUID id){
        contaRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaResponseDTO> atualizarConta(@RequestBody ContaRequestDTO contaRequestDTO, @PathVariable UUID id)throws Exception{

        Conta contaExistente = contaRepository.findById(id).orElseThrow(() -> new Exception(" Conta não Existe. "));

        contaExistente.setNomeTitular(contaRequestDTO.getNomeTitular());
        contaExistente.setNumeroConta(contaRequestDTO.getNumeroConta());
        contaExistente.setNumeroAgencia(contaRequestDTO.getNumeroAgencia());
        contaExistente.setChavePix(contaRequestDTO.getChavePix());

        contaExistente = contaRepository.save(contaExistente);

        ContaResponseDTO contaResponseDTO = ContaResponseDTO.builder()
                .id(contaExistente.getId())
                .nomeTitular(contaExistente.getNomeTitular())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(contaResponseDTO);
    }



    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> consultaContaPorID(@PathVariable UUID id) throws Exception{

        Conta conta = contaRepository.findById(id).orElseThrow(() -> new ContaNaoExisteException("Conta não existe."));

        ContaDTO contaDTO = ContaDTO.builder()
                .id(conta.getId())
                .nomeTitular(conta.getNomeTitular())
                .numeroAgencia(conta.getNumeroAgencia())
                .numeroConta(conta.getNumeroConta())
                .chavePix(conta.getChavePix())
                .saldo(conta.getSaldo())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(contaDTO);
    }

}
