package com.bootcamp.conta.service.controller;


import com.bootcamp.conta.service.dto.ContaRequestDTO;
import com.bootcamp.conta.service.model.Conta;
import com.bootcamp.conta.service.repository.ContaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ContaControllerTest  {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContaRepository contaRepository;

    //Objeto -> JSON
    @Autowired
    private ObjectMapper objectMapper;

    //Criacao de cenarios
    @Test
    void deveCriarAContaComSucesso() throws Exception{

        ContaRequestDTO request = ContaRequestDTO.builder()
                .nomeTitular("Cliente")
                .numeroAgencia(10)
                .numeroConta(20)
                .chavePix("cliente@pix.com")
                .build();

        UUID idDaContaSalva = UUID.randomUUID();

        Conta contaMock = Conta.builder()
                .id(idDaContaSalva)
                .nomeTitular(request.getNomeTitular())
                .numeroAgencia(request.getNumeroAgencia())
                .numeroConta(request.getNumeroConta())
                .chavePix(request.getChavePix())
                .saldo(BigDecimal.ZERO)
                .build();

//        when(contaRepository.findByNomeTitularAndNumeroContaAndChavePix(
//                request.getNomeTitular(),
//                request.getNumeroConta(),
//                request.getChavePix()
//        )).thenReturn(Optional.empty());

        when(contaRepository.save(any())).thenReturn(contaMock);

        mockMvc.perform(post("/api/contas")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("nomeTitular").value("Cliente"))
                .andExpect(status().isCreated());
        //Avalio a criação, e o que foi retornado.
    }

    @Test
    void deveBuscarContaComSucesso() throws Exception {

        mockMvc.perform(
                post("/api/contas")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }



}
