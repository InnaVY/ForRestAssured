
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.apache.http.HttpStatus.*;

public class MainTest {
    private String token = "6e398124a1dd58b3e41e21ec5e41a269137a8771586f4fc839091bcc99606ed3";
    private String uri="https://gorest.co.in/public/v1/users";
    /**
     * This test creates new user and asserts the success of
      the creation by status code 201 and equality of every field*/

    @Test
    public void postRestAssured(){
        RestAssured.baseURI=uri;
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

    @Test
    public void e2eApiWorkflow(){
        //Creation of User data
        User userRequest= new User();
        //New user creation
        Response userCreation= userCreation(userRequest);
        //Assertion of status Code and fields of created user
                userCreation.
                then().
                statusCode(SC_CREATED).
                assertThat().body("data.name", Matchers.equalTo(userRequest.getName())).
                assertThat().body("data.email", Matchers.equalTo(userRequest.getEmail())).
                assertThat().body("data.gender", Matchers.equalTo(userRequest.getGender())).
                assertThat().body("data.status", Matchers.equalTo(userRequest.getStatus())).
                log().
                all();
       //Getting of  user id
        JsonPath jsonPath = userCreation.jsonPath();
        int id = jsonPath.get("data.id");

       //Updating user data (changing of name, email)
       User userUpdated = new User(userRequest);
       //Changing user data
       Response userUpdate = userUpdate(userUpdated, id);
       //Assertion of status code and updated fields of user
        userUpdate.
                then().
                statusCode(SC_OK).
                assertThat().body("data.name", Matchers.equalTo(userUpdated.getName())).
                assertThat().body("data.email", Matchers.equalTo(userUpdated.getEmail())).
                assertThat().body("data.status", Matchers.equalTo(userUpdated.getStatus())).
                log().
                all();

        //Deletion of the user
        Response userDelete =given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                header("Authorization", "Bearer "+token).
                when().
                delete(uri+"/"+id);
        //Assertion of Status Code
          userDelete.then().
          statusCode(SC_NO_CONTENT).
          log().all();
          //Checking for absence of the deleted user
        Response getAbsent= given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                when().get(uri+"/"+id);

           getAbsent.then()
                   .statusCode(SC_NOT_FOUND)
                   .log().all();

    }
   private Response userCreation(User user){
       return given().
               header("Content-Type", "application/json").
               contentType(ContentType.JSON).
               accept(ContentType.JSON).
               header("Authorization", "Bearer "+token).
               body(user.toJString()).
               when().
               post(uri);

   }
   private Response userUpdate(User user, int id){
       return given().
               header("Content-Type", "application/json").
               contentType(ContentType.JSON).
               accept(ContentType.JSON).
               header("Authorization", "Bearer "+token).
               body(user.toJStringUpdate()).
               when().
               put(uri+"/"+id);

   }

}
