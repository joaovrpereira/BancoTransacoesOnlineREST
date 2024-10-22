package com.bootcamp.conta.service.repository;


import com.bootcamp.conta.service.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContaRepository extends JpaRepository<Conta, UUID> {

    //Optional: Forma de retornar um valor que pode ou não ser nulo
    Optional<Conta> findByChavePix(String chavePixPagador);

    Optional<Conta> findByNomeTitularAndNumeroContaAndChavePix(String nomeTitular,Integer numeroConta, String chavePix);

    /*
    jpql: Linguagem de escrita, na ideia de Objeto (Hibernate utiliza)

    @Query("SELECT conta FROM Conta conta WHERE conta.numeroConta = :numeroConta AND conta.chavePix = :chavePix AND conta.saldo > 10")
    Optional<Conta> findByNumeroContaAndChavePixAndSaldoMaiorQue10(Integer numeroConta, String chavePix);
    */

}
