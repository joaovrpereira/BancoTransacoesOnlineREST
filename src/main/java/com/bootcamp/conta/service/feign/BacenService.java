package com.bootcamp.conta.service.feign;

import com.bootcamp.conta.service.exceptions.ErroIntegracaoBacenException;
import com.bootcamp.conta.service.feign.dto.ChaveRequestDTO;
import com.bootcamp.conta.service.feign.dto.ChaveResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BacenService {

    private final BacenClient bacenClient;

    public ChaveResponseDTO criarChave(final String chave){
        try{

            ChaveRequestDTO chaveRequestDTO = ChaveRequestDTO.builder()
                    .chave(chave)
                    .ativa(Boolean.TRUE)
                    .build();

            return bacenClient.criarChave(chaveRequestDTO);
        } catch(Exception exc) {
            log.error("Erro ao chamar a API de cadastrar chave do Bacen", exc);
            throw new ErroIntegracaoBacenException("Erro ao cadastrar chave no Bacen", exc);
        }
    }

    public ChaveResponseDTO buscaChave(final String chave){
        try{
            return bacenClient.buscaChave(chave);
        } catch(Exception exc) {
            log.error("Erro ao chamar a API de buscar chave do Bacen", exc);
            throw new ErroIntegracaoBacenException("Erro ao buscar chave no Bacen", exc);
        }
    }




}
