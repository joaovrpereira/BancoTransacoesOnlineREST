package com.bootcamp.conta.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor
public class ContaRequestDTO {

    @NotEmpty(message = "É necessário informar um nome.")
    private String nomeTitular;
    @NotNull(message = "Obrigatório informar o número da Agência.")
    private Integer numeroAgencia;
    @NotNull(message = "Obrigatório informar o número da Conta.")
    private Integer numeroConta;
    @NotEmpty
    private String chavePix;

}
