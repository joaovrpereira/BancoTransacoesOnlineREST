package com.bootcamp.conta.service.feign;

import com.bootcamp.conta.service.feign.dto.ChaveRequestDTO;
import com.bootcamp.conta.service.feign.dto.ChaveResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(contextId = "BacenClient", name= "Bacen", url = "http://localhost:9001/api/bacen")
public interface BacenClient {

    @PostMapping(value = "/chaves")
    ChaveResponseDTO criarChave(ChaveRequestDTO chaveRequestDTO);

    @GetMapping(value = "/chaves/{chave}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ChaveResponseDTO buscaChave(@PathVariable final String chave);
}
