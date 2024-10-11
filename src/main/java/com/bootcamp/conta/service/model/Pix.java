package com.bootcamp.conta.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Pix {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private String chavePixPagador;

    @Column
    private BigDecimal valor;


    //Uma conta pode fazer vários pix
    @ManyToOne
    @JoinColumn(name= "conta_id") //Qual coluna da tabela, o Hibernate faz o vínculo. Conta_id seria o Foreign Key
    private Conta conta;

}
