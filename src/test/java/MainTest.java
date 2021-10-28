
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.apache.http.HttpStatus.SC_CREATED;
import org.json.*;
import static io.restassured.RestAssured.*;

public class MainTest {

    /**
     * This test creates new user and asserts the success of
      the creation by status code 201 and equality of every field*/

    @Test
    public void postTest(){
        User userRequest= new User(); //the User object creation with generation of fake data
        String token = "6e398124a1dd58b3e41e21ec5e41a269137a8771586f4fc839091bcc99606ed3";

      String response;
        response = given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                auth().
                oauth2(token).
                body(userRequest.toJString()).//the given String is in JSON format
                when().
                log().
                all().
                post("https://gorest.co.in/public/v1/users").
                then().
                assertThat().
                statusCode(SC_CREATED).
                extract().asString();

        JSONObject responseObj = new JSONObject(response);
        User userResponse = new User(responseObj);//the User object creation with data from response

        //assertion of response using testNG
        assertEquals(userResponse.getName(), userRequest.getName());
        assertEquals(userResponse.getEmail(), userRequest.getEmail());
        assertEquals(userResponse.getGender(), userRequest.getGender());
        assertEquals(userResponse.getStatus(), userRequest.getStatus());
    }

}
