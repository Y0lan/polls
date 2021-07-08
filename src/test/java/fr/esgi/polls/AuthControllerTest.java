package fr.esgi.polls;

import fr.esgi.polls.payload.LoginRequest;
import net.minidev.json.JSONObject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static io.restassured.RestAssured.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AuthControllerTest {

    @Value("${server.servlet.context-path}")
    private String apiPath;

    @Value("${server.port}")
    private String apiPort;


    @Test
    void userShouldSignUp(){

        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "Rharbaoui");
        requestBody.put("username", "rayann");
        requestBody.put("email", "toto@toto.fr");
        requestBody.put("password", "tototata");

        given()
                .contentType("application/json")
                .body(requestBody)
                .post("http://localhost:" + apiPort + apiPath + "/auth/signup")
                .then()
                .assertThat()
                .statusCode(200)
                .body("token", Matchers.notNullValue());
    }

    @Test
    void userShouldSignIn(){

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("toto@toto.fr");
        loginRequest.setPassword("tototata");

        JSONObject requestBody = new JSONObject();
        requestBody.put("loginRequest", loginRequest.toString());

        given()
                .contentType("application/json")
                .body(requestBody)
                .post("http://localhost:" + apiPort + apiPath + "/auth/signin")
                .then()
                .assertThat()
                .statusCode(200)
                .body("token", Matchers.notNullValue());
    }
}
