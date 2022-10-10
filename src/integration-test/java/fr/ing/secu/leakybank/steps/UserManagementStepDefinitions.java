package fr.ing.secu.leakybank.steps;

import fr.ing.secu.leakybank.UserSession;
import io.cucumber.java8.En;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.context.embedded.LocalServerPort;

import static io.restassured.RestAssured.given;

public class UserManagementStepDefinitions implements En {

    @LocalServerPort
    private Integer localPort = 8080;

    private static Response response;
    private static boolean isAdmin;

    public UserManagementStepDefinitions() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/";
        
        Before(()-> {
            response = null;
            isAdmin = false;
        });

        Given("[UserManagement] {word} is connected", (String profile) ->{
            //System.out.println("Given ok");
            isAdmin = "admin".equals(profile);

            RequestSpecification request = given().port(localPort).request();
            if(isAdmin){
                request.formParam("login", "admin").formParam("password", "adminP@ssw");
            } else {
                request.formParam("login", "alice").formParam("password", "aliceP@ssw");
            }
        });

        When("[UserManagement] the {word} try to delete a user account", (String profile) -> {
            RequestSpecification request = given().port(localPort).request();
            response = request.when().delete("admin/users/bob/delete");
        });

        Then("[UserManagement] the response is {word}", (String status) -> {
            //System.out.println("Then ok");
            if("ok".equals(status)){
                response.then().statusCode(200);
            } else {
                response.then().statusCode(200);
            }
        });
    }

}
