package com.bootcamp.bacen.service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

//No default constructor for entity 'com.bootcamp.bacen.service.model.Chave'

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Chave {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String chave;
    @Column
    private Boolean ativa;
}
