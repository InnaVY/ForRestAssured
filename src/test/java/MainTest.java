
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import static org.apache.http.HttpStatus.*;


public class MainTest {

    private UserRequestPayload userRequestPayload = new UserRequestPayload();
    /**
     * This test creates new user and asserts the success of
      the creation by status code 201 and equality of every field*/

    @Test
    public void postRestAssured(){
        //Creation of User data
        User userRequest= new User();
        //New user creation
        Response userCreation= userRequestPayload.createUser(userRequest);

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
      Response userCreation= userRequestPayload.createUser(userRequest);

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

       //Changing of the created user data
       Response userUpdateResponse= userRequestPayload.updateUser(userUpdated, id);

       //Assertion of Status Code (200) and fields (name, email, status)
       userUpdateResponse.
               then().
               statusCode(SC_OK).
               assertThat().body("data.name", Matchers.equalTo(userUpdated.getName())).
               assertThat().body("data.email", Matchers.equalTo(userUpdated.getEmail())).
               assertThat().body("data.status", Matchers.equalTo(userUpdated.getStatus())).
               log().
               all();
       //Deletion of the changed user
      Response userDeleteResponse= userRequestPayload.deleteUser(id);

       //Status Code assertion (204) of user deletion
       userDeleteResponse.then().
               statusCode(SC_NO_CONTENT).
               log().all();

       //Checking for the absence of the deleted user (Status Code = 404)
      Response getDeletedUserResponse= userRequestPayload.getDeletedUser(id);

       getDeletedUserResponse.then()
               .statusCode(SC_NOT_FOUND)
               .log().all();
   }


}
