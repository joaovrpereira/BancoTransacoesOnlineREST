package com.bootcamp.conta.service.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Representa a tabela Conta no Banco de Dados
// @Table(name = "CONTA-PIX") Usar se o nome da tabela no banco for CONTA-PIX

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CONTA")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) //AUTO passa a responsabilidade para o Hibernate. Table Banco de dados faz
    private UUID id;

    //@Column(name="NOME_TITULAR")
    @Column
    private String nomeTitular;

    @Column
    private Integer numeroAgencia;

    @Column
    private Integer numeroConta;

    @Column
    private String chavePix;

    @Column
    private BigDecimal saldo = BigDecimal.ZERO;

    /*
    Cascade: cascata de fluxo de alterações
    orphanRemoval: Pix sem referencia com Conta são deletados

    Pelo lado performático, não é viável retornar a lista. Quando trabalhamos com listas em banco, devemos analisar se vamos precisar dos dados.

    FetchType.EAGER = carrego o objeto Conta no banco, já carrego a lista de pix, mesmo desnecessariamente
    FetchType.LAZY = só trago do banco se precisar das informações

    fetch = FetchType.EAGER, mesmo retornando somente GetNome, ele carrega o Objeto por inteiro,
    fazendo com que o banco faca um left join com Pix desnecessariamente
    Além disso, vai ser realizada um left join para cada conta cadastrada no banco. Péssimo para a perfomance.
     */

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true) //fetch = FetchType.EAGER
    private List<Pix> historicoPix = new ArrayList<>();

    public void sacar(BigDecimal valor){
        saldo = this.saldo.subtract(valor);
    }

    public void depositar(BigDecimal valor){
        saldo = this.saldo.add(valor);
    }




}
