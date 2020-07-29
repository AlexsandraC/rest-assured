/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package rest.assured.teste;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.assured.dominio.Usuario;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class UsuarioTeste {
    @BeforeClass
    public static void setup(){

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        baseURI = "https://reqres.in";
        basePath = "/api";
    }
    @Test public void testListUserMetadata() {
        given().
                param("page", "2").
        when().
                get("/users").
        then().
                statusCode(HttpStatus.SC_OK).
                body("page", is(2)).
                body("data", is(notNullValue()));
    }

    @Test public void testSucessfullyCreateaUser(){
        Usuario usuario = new Usuario("rafael", "eng test", "email@gmail.com");
        given().
                contentType(ContentType.JSON).
                body(usuario).
        when().
                post("/users").
        then().
                statusCode(HttpStatus.SC_CREATED).
                body("name", is("rafael"));
    }
}
