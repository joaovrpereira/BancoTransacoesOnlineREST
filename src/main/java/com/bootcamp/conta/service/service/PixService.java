package com.bootcamp.conta.service.service;


import com.bootcamp.conta.service.dto.PixRequestDTO;
import com.bootcamp.conta.service.dto.PixResponseDTO;
import com.bootcamp.conta.service.exceptions.ContaNaoExisteException;
import com.bootcamp.conta.service.exceptions.ErroIntegracaoBacenException;
import com.bootcamp.conta.service.exceptions.SaldoInsuficienteException;
import com.bootcamp.conta.service.feign.BacenService;
import com.bootcamp.conta.service.model.Conta;
import com.bootcamp.conta.service.model.Pix;
import com.bootcamp.conta.service.repository.ContaRepository;
import com.bootcamp.conta.service.repository.PixRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PixService {

    private final PixRepository pixRepository;
    private final ContaRepository contaRepository;
    private final BacenService bacenService;

    @Transactional(
            rollbackFor = {
                    ErroIntegracaoBacenException.class,
                    ContaNaoExisteException.class,
                    SaldoInsuficienteException.class
            }
    )
    public PixResponseDTO realizaPix(PixRequestDTO pixRequestDTO){

        Optional<Pix> existingPix = pixRepository.findByIdempotencia(pixRequestDTO.getIdempotencia());

        if(existingPix.isPresent()){
            return new PixResponseDTO(existingPix.get().getCreatedAt(), "Pix já foi processado com sucesso (idempotente).", existingPix.get());
        }

        Optional<Conta> contaPagadorOptional = contaRepository.findByChavePix(pixRequestDTO.getChavePixPagador());

        if(contaPagadorOptional.isEmpty()){
            throw new ContaNaoExisteException(String.format("Conta com a chave %s não existe.", pixRequestDTO.getChavePixPagador()));
        }

        Conta contaPagador = contaPagadorOptional.get();

        if(pixRequestDTO.getValor().compareTo(contaPagador.getSaldo()) > 0){
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar o Pix.");
        }

        String chavePagador = bacenService.buscaChave(contaPagador.getChavePix()).getChave();

        contaPagador.sacar(pixRequestDTO.getValor());
        contaPagador = contaRepository.save(contaPagador);

        Optional<Conta> contaRecebedorOptional = contaRepository.findByChavePix(pixRequestDTO.getChavePixRecebedor());

        if(contaRecebedorOptional.isEmpty()){
            throw new ContaNaoExisteException(String.format("Conta com a chave %s não existe", pixRequestDTO.getChavePixRecebedor()));
        }

        Conta contaRecebedor = contaRecebedorOptional.get();
        String chaveRecebedor = bacenService.buscaChave(contaRecebedor.getChavePix()).getChave();

        contaRecebedor.depositar(pixRequestDTO.getValor());
        contaRepository.save(contaRecebedor);

        Pix pix = Pix.builder()
                .createdAt(LocalDateTime.now())
                .conta(contaPagador)
                .chavePixPagador(chavePagador)
                .chavePixRecebedor(chaveRecebedor)
                .valor(pixRequestDTO.getValor())
                .idempotencia(pixRequestDTO.getIdempotencia())
                .build();

        pixRepository.save(pix);

        PixResponseDTO pixResponseDTO = new PixResponseDTO();
        pixResponseDTO.setPix(pix);
        pixResponseDTO.setMessage("Pix realizado com sucesso.");

        return pixResponseDTO;
    }

}
