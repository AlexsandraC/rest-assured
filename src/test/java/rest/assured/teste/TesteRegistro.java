package rest.assured.teste;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.assured.dominio.Usuario;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class TesteRegistro extends TesteBase {

    private static final String REGISTRA_USUARIO_ENDPOINT = "/register";
    private static final String LOGIN_USUARIO_ENDPOINT = "/login";

    @BeforeClass
    public static void setupRegistro(){
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .build();
    }

    @Test
    public void testeNaoEfetuaRegistroQuandoSenhaEstaFaltando(){
        Usuario usuario = new Usuario();
        usuario.setEmail("sydney@life");
        given().
                body(usuario).
        when().
                post(REGISTRA_USUARIO_ENDPOINT).
        then().
                body("error", is("Missing password"));
    }
//Este teste deveria estar no TesteLogin, porém esta aqui para mostrar a funcionalidade de múltiplo setups e ResponseSepec
    @Test
    public void testeLoginNaoEfetuadoQuandoSenhaEstaFaltando(){
        Usuario usuario = new Usuario();
        usuario.setEmail("sydney@life");
        given().
                body(usuario).
                when().
                post(LOGIN_USUARIO_ENDPOINT).
                then().
                body("error", is("Missing password"));
    }
}
