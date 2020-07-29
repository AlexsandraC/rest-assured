/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package rest.assured.teste;

import org.apache.http.HttpStatus;
import org.junit.Test;
import rest.assured.dominio.Usuario;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TesteUsuario extends TesteBase {

    private static final String LISTA_USUARIOS_ENDPOINT = "/users";
    private static final String CRIAR_USUARIO_ENDPOINT = "/user";
    private static final String MOSTRAR_USUARIO_ENDPOINT = "/users/{userId}";

    @Test public void testeMostraPaginaEspecifica() {
        given().
                params("page", "2").
        when().
                get(LISTA_USUARIOS_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
                body("page", is(2)).
                body("data", is(notNullValue()));
    }

    @Test public void testeCriaUsuarioComSucesso(){
        Usuario usuario = new Usuario("rafael", "eng test", "email@gmail.com", "lima");
        given().
                body(usuario).
        when().
                post(CRIAR_USUARIO_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_CREATED).
                body("name", is("rafael"));
    }

    @Test public void testeTamanhoDosItensMostradosIgualAoPerPage() {
        int paginaEsperada = 2;
        int perPageEsperado = retornaPerPageEsperado(paginaEsperada);

        given().
                param("page", paginaEsperada).
                when().
                get(LISTA_USUARIOS_ENDPOINT).
                then().
                statusCode(HttpStatus.SC_OK).
                body(
                        "page", is(paginaEsperada),
                        "data.size()", is(perPageEsperado),
                        "data.findAll { it.avatar.startsWith('https://s3.amazonaws.com') }.size()", is(perPageEsperado)
                );

    }

    @Test
    public void testeMostraUsuarioEspecifico(){
        Usuario usuario = given().
                pathParam("userId", 2).
        when().
                get(MOSTRAR_USUARIO_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
        extract().
                body().jsonPath().getObject("data", Usuario.class);

        assertThat(usuario.getEmail(), containsString("@reqres.in"));
        assertThat(usuario.getName(), is("Janet"));
        assertThat(usuario.getLastname(), is("Weaver"));

    }

    private int retornaPerPageEsperado(int page) {
        return given().
                    param("page", page).
                when().
                    get(LISTA_USUARIOS_ENDPOINT).
                then().
                    statusCode(HttpStatus.SC_OK).
                extract().
                    path("per_page");
    }
}
