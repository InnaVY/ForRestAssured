import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import property.manager.PropertyManager;

import static io.restassured.RestAssured.given;


public class UserRequestPayload {
    private String url;
    private String token;
    private RequestSpecification requestSpecification = null;


    public UserRequestPayload() {
        PropertyManager.setProperties();
       this.url = PropertyManager.getProperty("url");
       this.token  = PropertyManager.getProperty("token");
       this.requestSpecification = new RequestSpecBuilder().
        setBaseUri(url).
        setContentType(ContentType.JSON).
        setAccept(ContentType.JSON).
                addHeader("Content-Type","application/json").
                addHeader("Authorization","Bearer "+token).build();

    }

    public  Response createUser(User user){
        return given().spec(requestSpecification).
                body(user.getUserJsonForm().toJStringNew()).
                when().
                post();

    }
    public  Response createModifiedUser(String modified){
        return given().spec(requestSpecification).
                body(modified).
                when().
                post();

    }
    public Response updateUser(User userUpdated, int id){
        return given().spec(requestSpecification).
                body(userUpdated.toJStringUpdate()).
                when().
                put(url+"/"+id);

    }
    public Response updateModifiedUser(String userUpdated, int id){
        return given().spec(requestSpecification).
                body(userUpdated).
                when().
                put(url+"/"+id);

    }
    public Response deleteUser(int id){
        //Deletion of the user
        return given().spec(requestSpecification).
                when().
                delete(url+"/"+id);

    }

    public Response getUser(int id) {
       return given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                when().get(url+"/"+id);


    }
    public  Response createUserWithoutToken(User user) {
        return given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                // header("Authorization", "Bearer "+token).
                        body(user.getUserJsonForm().toJStringNew()).
                when().
                post(url);
    }
    public  Response createUserWithInvalidToken(User user) {
       String invalidToken = token.substring(5, token.length()-3);
        return given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                header("Authorization", "Bearer "+invalidToken).
                        body(user.getUserJsonForm().toJStringNew()).
                when().
                post(url);
    }
    public  Response createUserWithXmlContentType(User user) {

        return given().
                header("Content-Type", "application/xml").
                contentType(ContentType.XML).
                accept(ContentType.XML).
                header("Authorization", "Bearer "+token).
                body(user.getUserJsonForm().toJStringNew()).
                when().
                post(url);
    }
    public Response updateUserWithoutToken(User userUpdated, int id){
        return given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
               // header("Authorization", "Bearer "+token).
                body(userUpdated.getUserJsonForm().toJStringUpdate()).
                when().
                put(url+"/"+id);

    }
    public Response deleteUserWithoutToken(int id){
        //Deletion of the user
        return given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
               // header("Authorization", "Bearer "+token).
                when().
                delete(url+"/"+id);

    }
    }
