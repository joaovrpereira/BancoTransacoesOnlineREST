package com.bootcamp.bacen.service.controller;

import com.bootcamp.bacen.service.dto.ChaveRequestDTO;
import com.bootcamp.bacen.service.dto.ChaveResponseDTO;
import com.bootcamp.bacen.service.service.ChaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RequestMapping("/api/bacen/chaves")
@RestController
@RequiredArgsConstructor
public class ChaveController {

    private final ChaveService chaveService;

    @PostMapping
    public ResponseEntity<ChaveResponseDTO> criarChave(@RequestBody ChaveRequestDTO chaveRequestDTO){
        return ResponseEntity.status(CREATED).body(chaveService.criarChave(chaveRequestDTO));
    }

    @GetMapping("/{chave}")
    public ResponseEntity<ChaveResponseDTO> conta(@PathVariable String chave){
        return ResponseEntity.status(OK).body(chaveService.buscarChaveByChave(chave));
    }

}
