
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import static org.apache.http.HttpStatus.SC_CREATED;
import static io.restassured.RestAssured.*;

public class MainTest {
    private String token = "6e398124a1dd58b3e41e21ec5e41a269137a8771586f4fc839091bcc99606ed3";

    /**
     * This test creates new user and asserts the success of
      the creation by status code 201 and equality of every field*/

    @Test
    public void postRestAssured(){
        RestAssured.baseURI="https://gorest.co.in/public/v1/users";
        User userRequest= new User();
       given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+token)
                .body(userRequest.toJString())
                .when()
                .post(baseURI)
                       .then()
                       .statusCode(SC_CREATED)
                       .assertThat().body("data.name", Matchers.equalTo(userRequest.getName()))
                       .assertThat().body("data.email", Matchers.equalTo(userRequest.getEmail()))
                       .assertThat().body("data.gender", Matchers.equalTo(userRequest.getGender()))
                       .assertThat().body("data.status", Matchers.equalTo(userRequest.getStatus()))
                       .log()
                       .all();


    }
}
