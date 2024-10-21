package com.bootcamp.conta.service.controller.integration;


import com.bootcamp.conta.service.model.Conta;
import com.bootcamp.conta.service.repository.ContaRepository;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContaControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(
                    wireMockConfig().port(9001)
            )
            .build();


    @Autowired
    private ContaRepository contaRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void afterEach() {
        clearDatabase(contaRepository);
    }


    protected void clearDatabase(JpaRepository... repositories){
        Arrays.stream(repositories).forEach(CrudRepository::deleteAll);
    }


    //Utilizando arquivos JSON
    @Test
    void deveCriarContaComSucesso() throws IOException {

        /*
        ContaRequestDTO request = ContaRequestDTO.builder()
                .nomeTitular("Samuel")
                .numeroAgencia(10)
                .numeroConta(20)
                .chavePix("samuel@pix.com").build();
        */

        String request = new String(Files.readString(Paths.get("src/test/resources/request-conta.json")));

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/contas")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("nomeTitular", equalTo("Samuel"));

    }

    @Test
    void deveRetornarErrorPorNaoCriarChaveNoBacen() throws IOException {

        String request = new String(Files.readString(Paths.get("src/test/resources/request-conta-erro-bacen.json")));

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/contas")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("detail", equalTo("Erro ao cadastrar chave no Bacen"));

    }

    @Test
    void deveBuscarContaComSucesso(){

        Conta conta = Conta.builder()
                .nomeTitular("Samuel")
                .numeroAgencia(10)
                .numeroConta(20)
                .chavePix("samuel@pix.com")
                .saldo(BigDecimal.ZERO).build();

        contaRepository.save(conta);


        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/contas")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("[0].nomeTitular", equalTo("Samuel"))
                .body("[0].numeroAgencia", equalTo(10))
                .body("[0].numeroConta", equalTo(20))
                .body("[0].chavePix", equalTo("samuel@pix.com"));

    }

    @Test
    void deveRecusarACriacaoDeContaJaExistente() throws IOException{

        Conta conta = Conta.builder()
                .nomeTitular("Anibal de Souza Pereira")
                .numeroAgencia(13)
                .numeroConta(20220)
                .chavePix("999")
                .saldo(BigDecimal.ZERO).build();

        contaRepository.save(conta);

        String request = new String(Files.readString(Paths.get("src/test/resources/request-contaJaExiste.json")));


        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/api/contas")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CONFLICT.value());


    }
}
