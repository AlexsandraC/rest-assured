package rest.assured.teste;

import org.apache.http.HttpStatus;
import org.junit.Test;
import rest.assured.dominio.Usuario;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class TesteRegistro extends TesteBase {

    private static final String REGISTRA_USUARIO_ENDPOINT = "/register";

    @Test
    public void testeNaoEfetuaRegistroQuandoSenhaEstaFaltando(){
        Usuario usuario = new Usuario();
        usuario.setEmail("sydney@life");
        given().
                body(usuario).
        when().
                post(REGISTRA_USUARIO_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("error", is("Missing password"));
    }
}
