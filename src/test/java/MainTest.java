
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import org.json.JSONObject;
import org.testng.log4testng.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.Locale;

import static io.restassured.RestAssured.*;

public class MainTest {
    private static Logger logger = Logger.getLogger(MainTest.class);

    /**this test creates new user and asserts the success of
      the creation by status code 201*/

    @Test
    public void postTest(){
        /**creation of Faker object for
         random test data generation*/

        Faker faker = new Faker(new Locale("en-GB"));
        logger.info("Creation of Faker object for random test data generation");
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String gender = faker.random().nextBoolean()?"male":"female";
        String status = "active";

     JSONObject request = new JSONObject();

     request.put("name", name);
     request.put("email", email);
     request.put("gender", gender);
     request.put("status",status);
    logger.info("Request user:/n"+ request.toString());
     String token = "6e398124a1dd58b3e41e21ec5e41a269137a8771586f4fc839091bcc99606ed3";

     String response=  given().
             header("Content-Type", "application/json").
             contentType(ContentType.JSON).
             accept(ContentType.JSON).
             auth().
             oauth2(token).
             body(request.toString()).
             when().
             post("https://gorest.co.in/public/v1/users").
             then().
             statusCode(201)
            .extract().asString();
    System.out.println(response);
    assertThat(response.contains("\"name\":\""+name), is(true));
    assertThat(response.contains("\"email\":\""+email), is(true));
    assertThat(response.contains("\"gender\":\""+gender), is(true));
    assertThat(response.contains("\"status\":\""+status), is(true));

    }

}
