
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

    /**
     * This test is checking e2e api workflow:
     * 1) Create user
     * 2) Update user
     * 3) Delete user
     * 4) Check that deleted user is absent
     */
   @Test
    public void e2eApiWorkflow2(){
      //Creation of User data
       User userRequest= new User();

       //New user creation
      Response userCreation= UserGRUDOperations.createUser(userRequest, token, uri);

       //Assertion of status Code and fields of created user
       UserGRUDOperations.assertionOfUserCreation(userCreation, userRequest);

       //Getting of  user id
       JsonPath jsonPath = userCreation.jsonPath();
       int id = jsonPath.get("data.id");

       //Updating user data (changing of name, email)
       User userUpdated = new User(userRequest);

       /*Changing of the created user data and assertion of Status Code (200)
       *and fields (name, email, status)
        */
       UserGRUDOperations.updateUserAndAssert(userUpdated, id, token, uri);

       //Deletion of the changed user and Status Code assertion (204)
       UserGRUDOperations.deleteUserAndAssert(id, token, uri);

       //Checking for the absence of the deleted user
       UserGRUDOperations.getDeletedUserAndAssert(id, uri);
   }
}
