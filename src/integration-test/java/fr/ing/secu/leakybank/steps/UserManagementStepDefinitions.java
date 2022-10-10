package fr.ing.secu.leakybank.steps;

import fr.ing.secu.leakybank.dao.UsersDAO;
import io.cucumber.java8.En;
import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;

import static io.restassured.RestAssured.given;


public class UserManagementStepDefinitions implements En {

    @LocalServerPort
    private Integer localPort = 8080;

    @Autowired
    private UsersDAO usersDAO;

    private static Response response;
    private static boolean isAdmin;
    private static RequestSpecification request;

    public UserManagementStepDefinitions() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/";
        
        Before(()-> {
            response = null;
        });

        Given("{word} is connected", (String profile) ->{
            //System.out.println("Given ok");
            isAdmin = "admin".equals(profile);

            if(isAdmin){
                request = given().port(localPort).request().given().auth().form("admin", "adminP@ssw",
                        new FormAuthConfig("/login", "login", "password"));
            } else {
                request = given().port(localPort).request().given().auth().form("alice", "aliceP@ssw",
                        new FormAuthConfig("/login", "login", "password"));
            }
        });

        When("the {word} try to delete a user account", (String profile) -> {
            response = request.when().delete("admin/users/bob/delete");
        });

        Then("the {word} {word} to delete the user account", (String profile, String status) -> {
            //System.out.println("Then ok");
            if("succeed".equals(status)){
                response.then().statusCode(200);
            } else {
                response.then().statusCode((500));
            }
        });

        Then("the user account still present in database", () -> {
            Assert.assertTrue(usersDAO.findUser("bob").isPresent());
        });

        Then("the user account is successfully deleted from database", () -> {
            Assert.assertFalse(usersDAO.findUser("bob").isPresent());
        });
    }

}
