
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Reporter;
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
      Response getDeletedUserResponse= userRequestPayload.getUser(id);

       getDeletedUserResponse.then()
               .statusCode(SC_NOT_FOUND)
               .log().all();
   }

   @Test(description = "Get absent user")
    public void getAbsentUser(){
       int id = (int) System.currentTimeMillis();
       Response response = userRequestPayload.getUser(id);
       //Status Code 404 assertion and message assertion
       response.then().
               statusCode(SC_NOT_FOUND).
               assertThat().body("data.message", Matchers.equalTo("Resource not found")).
               log().all();
   }
   @Test (description ="Create user with wrong user fields",
          dataProvider = "All fields",
          dataProviderClass = UserDataProvider.class)
    public void createWithWrongFields(String field, String changes, String message){
       //Creation of user with correct data
       User user = new User();
       //Creation the JSON String with wrong User data
       String request = user.getUserJsonForm().wrongJsonData(field, changes);
       Reporter.log("field: "+field+" changes: "+changes, true);
       Reporter.log (request,true);
       Response response = userRequestPayload.createModifiedUser(request);
       response.
               then().
               statusCode(SC_UNPROCESSABLE_ENTITY).
               body("data[0].message", Matchers.equalTo(message)).
               log().all();
   }

   @Test(description = "Create with name/email that exists",
         dataProvider = "Name and Email fields",
         dataProviderClass = UserDataProvider.class)
    public void createWithExistingFiled(String field){
       Reporter.log("field: "+field, true);
       User user1 = new User();
       Response response1 = userRequestPayload.createUser(user1);
       JsonPath jsonPath = response1.jsonPath();
       //Getting field from created user
       String field1=jsonPath.get("data."+field);
       User user2 = new User();
       //Setting the same field to new User
       if (field.equals("name"))user2.setName(field1);
       if (field.equals("email"))user2.setEmail(field1);
       Response response2 = userRequestPayload.createUser(user2);
       response2.then().
               statusCode(SC_UNPROCESSABLE_ENTITY).
               body("data[0].message", Matchers.equalTo("has already been taken")).
               log().all();
   }
    @Test(description = "Update with name/email that exists",
            dataProvider = "Name and Email fields",
            dataProviderClass = UserDataProvider.class)
    public void updateWithExistingFiled(String field){
        Reporter.log("field: "+field, true);
        User user1 = new User();
        Response response1 = userRequestPayload.createUser(user1);
        //Getting name/email from created user
        JsonPath jsonPath = response1.jsonPath();
        String field1=jsonPath.get("data."+field);
        User user2 = new User();
        //Creation of second user with new fields
        Response response2 = userRequestPayload.createUser(user2);
        JsonPath jsonPath2 = response2.jsonPath();
        int id = jsonPath2.get("data.id");
        //Updating the second user with name/email of the first user
            User user3 = new User();
            if (field.equals("name"))user3.setName(field1);
            if (field.equals("email"))user3.setEmail(field1);
        Response  response3 = userRequestPayload.updateUser(user3,id);
            response3.then().
                    statusCode(SC_UNPROCESSABLE_ENTITY).
                    body("data[0].message", Matchers.equalTo("has already been taken")).
                    log().all();
        }
    @Test (description ="Update user with wrong user fields",
            dataProvider = "All fields",
            dataProviderClass = UserDataProvider.class)
    public void updateWithWrongFields(String field, String changes, String message){
        //Creation of user with correct data
        User user = new User();
        Response response1 = userRequestPayload.createUser(user);
        JsonPath jsonPath = response1.jsonPath();
        int id = jsonPath.get("data.id");
        //Creation of user2 with correct data
        User user2 = new User();
        //Creation the JSON String with wrong User data
        String request = user2.getUserJsonForm().wrongJsonData(field, changes);
        Reporter.log("field: "+field+" changes: "+changes, true);
        Reporter.log (request,true);
        Response response = userRequestPayload.updateModifiedUser(request, id);
        response.
                then().
                statusCode(SC_UNPROCESSABLE_ENTITY).
                body("data[0].message", Matchers.equalTo(message)).
                log().all();
    }
    @Test (description = "Create user with wrong JSON format")
    public void createWrongJsonFormat(){
        User user = new User();
        String wrongRequest = user.getUserJsonForm().wrongJsonFormat();
        Response response = userRequestPayload.createModifiedUser(wrongRequest);
        response.then()
                .statusCode(SC_BAD_REQUEST).
                log().all();
    }
    @Test (description = "Update user with wrong JSON format")
    public void updateWrongJsonFormat(){
       //Creation new user with correct data
        User user = new User();
        Response response1 = userRequestPayload.createUser(user);
        JsonPath jsonPath = response1.jsonPath();
        int id = jsonPath.get("data.id");
        //Updating user with wrong JSON format
        User user2 = new User();
        String wrongRequest = user2.getUserJsonForm().wrongJsonFormat();
        Response response = userRequestPayload.updateModifiedUser(wrongRequest, id);
        response.then()
                .statusCode(SC_BAD_REQUEST).
                log().all();
    }
    @Test(description = "Delete absent user")
    public void deleteAbsentUser(){
        int id = (int) System.currentTimeMillis();
        Response response = userRequestPayload.deleteUser(id);
        //Status Code 404 assertion and message assertion
        response.then().
                statusCode(SC_NOT_FOUND).
                assertThat().body("data.message", Matchers.equalTo("Resource not found")).
                log().all();
    }

    @Test(description = "Authentication is failed due to absent/invalid token",
          dataProvider = "about token", dataProviderClass = UserDataProvider.class)
    public void createWithoutToken(String aboutToken){
       User user = new User();
       Response response = null;
       if (aboutToken.equals("absent")) response = userRequestPayload.createUserWithoutToken(user);
       if (aboutToken.equals("invalid")) response =userRequestPayload.createUserWithInvalidToken(user);
       response.then().
                statusCode(SC_UNAUTHORIZED).
                assertThat().body("data.message", Matchers.equalTo("Authentication failed")).
                log().all();
    }

    @Test (description = "Update and Delete without token",
           dataProvider = "Update or delete", dataProviderClass = UserDataProvider.class)
    public void updateDeleteWithoutToken(String method){
       Reporter.log("method: "+method, true);
       User user = new User();
       Response response1 = userRequestPayload.createUser(user);
       JsonPath jsonPath = response1.jsonPath();
       int id = jsonPath.get("data.id");
       Response response = null;
       if (method.equals("update")){
           User user2 = new User();
           response = userRequestPayload.updateUserWithoutToken(user2,id);
        }
       if (method.equals("delete")) response = userRequestPayload.deleteUserWithoutToken(id);
       response.then().
               statusCode(SC_UNAUTHORIZED).
               assertThat().body("data.message", Matchers.equalTo("Authentication failed")).
               log().all();
    }
    @Test(description = "Application/XML")
    public void applicationXml(){
       User user = new User();
       Response response = userRequestPayload.createUserWithXmlContentType(user);
       response.then().
               statusCode(SC_UNPROCESSABLE_ENTITY).
               log().all();
    }
    }

