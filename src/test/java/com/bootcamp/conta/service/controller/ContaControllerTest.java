package com.bootcamp.conta.service.controller;


import com.bootcamp.conta.service.dto.ContaRequestDTO;
import com.bootcamp.conta.service.dto.ContaResponseDTO;
import com.bootcamp.conta.service.service.ContaService;
import com.bootcamp.conta.service.service.PixService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ContaControllerTest  {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContaService contaService;

    @MockBean
    private PixService pixService;

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

        ContaResponseDTO contaResponseDTO = ContaResponseDTO.builder()
                .id(idDaContaSalva)
                .nomeTitular(request.getNomeTitular()).build();

        when(contaService.criarConta(request)).thenReturn(contaResponseDTO);

        //Avalio a criação, e o que foi retornado.
        mockMvc.perform(post("/api/contas")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("nomeTitular").value("Cliente"))
                .andExpect(status().isCreated());

    }

    @Test
    void deveBuscarContaComSucesso() throws Exception {

        mockMvc.perform(
                get("/api/contas").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



}
