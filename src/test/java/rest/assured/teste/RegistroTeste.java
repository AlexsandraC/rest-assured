package rest.assured.teste;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.assured.dominio.Usuario;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class RegistroTeste extends BaseTeste {

    @Test
    public void testNaoEfetuaRegistroQuandoSenhaEstaFaltando(){
        Usuario usuario = new Usuario();
        usuario.setEmail("sydney@life");
        given().
                contentType(ContentType.JSON).
                body(usuario).
        when().
                post("/register").
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("error", is("Missing password"));
    }
}
