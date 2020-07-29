package rest.assured.teste;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.assured.dominio.Usuario;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TesteUsuarioOutraOpcaoo {
    private static final String BASE_URL = "https://reqres.in";
    private static final String BASE_PATH = "/api";

    private static final String LISTA_USUARIOS_ENDPOINT = "/users";
    private static final String CRIAR_USUARIO_ENDPOINT = "/user";
    private static final String MOSTRAR_USUARIO_ENDPOINT = "/users/{userId}";

     @BeforeClass
    public static void setup(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test public void testeMostraPaginaEspecifica() {
        String uri = getUri(LISTA_USUARIOS_ENDPOINT);

        given().
                params("page", "2").
                when().
                get(uri).
                then().
                contentType(ContentType.JSON).
                statusCode(HttpStatus.SC_OK).
                body("page", is(2)).
                body("data", is(notNullValue()));
    }

    @Test public void testeCriaUsuarioComSucesso(){
        String uri = getUri(CRIAR_USUARIO_ENDPOINT);
        Map<String, String> usuario = new HashMap<>();
        usuario.put("name","rafael");
        usuario.put("job","eng test");

        given().
                contentType(ContentType.JSON).
                body(usuario).
                when().
                post(uri).
                then().
                contentType(ContentType.JSON).
                statusCode(HttpStatus.SC_CREATED).
                body("name", is("rafael"));
    }

    @Test public void testeTamanhoDosItensMostradosIgualAoPerPage() {
        String uri = getUri(LISTA_USUARIOS_ENDPOINT);
        int paginaEsperada = 2;
        int perPageEsperado = retornaPerPageEsperado(paginaEsperada);

        given().
                param("page", paginaEsperada).
                when().
                get(uri).
                then().
                contentType(ContentType.JSON).
                statusCode(HttpStatus.SC_OK).
                body(
                        "page", is(paginaEsperada),
                        "data.size()", is(perPageEsperado),
                        "data.findAll { it.avatar.startsWith('https://s3.amazonaws.com') }.size()", is(perPageEsperado)
                );

    }

    @Test
    public void testeMostraUsuarioEspecifico(){
        String uri = getUri(MOSTRAR_USUARIO_ENDPOINT);
        Usuario usuario = given().
                pathParam("userId", 2).
                when().
                get(uri).
                then().
                contentType(ContentType.JSON).
                statusCode(HttpStatus.SC_OK).
                extract().
                body().jsonPath().getObject("data", Usuario.class);

        assertThat(usuario.getEmail(), containsString("@reqres.in"));
        assertThat(usuario.getName(), is("Janet"));
        assertThat(usuario.getLastname(), is("Weaver"));

    }

    private int retornaPerPageEsperado(int page) {
        String uri = getUri(LISTA_USUARIOS_ENDPOINT);
        return given().
                param("page", page).
                when().
                get(uri).
                then().
                contentType(ContentType.JSON).
                statusCode(HttpStatus.SC_OK).
                extract().
                path("per_page");
    }
    private String getUri(String endpoint) {
        return BASE_URL + BASE_PATH + endpoint;
    }
}

