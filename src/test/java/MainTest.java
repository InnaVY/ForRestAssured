
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import org.json.JSONObject;
import static io.restassured.RestAssured.*;

public class MainTest {

    /**this test creates new user and asserts the success of
      the creation by status code 201*/

    @Test
    public void postTest(){

     JSONObject request = new JSONObject();

     request.put("id", "1199");
     request.put("name", "VictorMalevsky5");
     request.put("email", "victor5@gmail.com");
     request.put("gender", "male");
     request.put("status","active");

     String token = "6e398124a1dd58b3e41e21ec5e41a269137a8771586f4fc839091bcc99606ed3";

      given().
             header("Content-Type", "application/json").
             contentType(ContentType.JSON).
             accept(ContentType.JSON).
             auth().
             oauth2(token).
             body(request.toString()).
             when().
             post("https://gorest.co.in/public/v1/users").
             then().
             statusCode(201);

    }

}
