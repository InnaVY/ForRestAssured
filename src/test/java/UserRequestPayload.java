import io.restassured.http.ContentType;
import io.restassured.response.Response;
import property.manager.PropertyManager;

import static io.restassured.RestAssured.given;


public class UserRequestPayload {
    private String url;
    private String token;


    public UserRequestPayload() {
        PropertyManager.setProperties();
       this.url = PropertyManager.getProperty("url");
       this.token  = PropertyManager.getProperty("token");
    }

    public  Response createUser(User user){
        return given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                header("Authorization", "Bearer "+token).
                body(user.toJString()).
                when().
                post(url);

    }

    public Response updateUser(User userUpdated, int id){
        return given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                header("Authorization", "Bearer "+token).
                body(userUpdated.toJStringUpdate()).
                when().
                put(url+"/"+id);

    }
    public Response deleteUser(int id){
        //Deletion of the user
        return given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                header("Authorization", "Bearer "+token).
                when().
                delete(url+"/"+id);

    }

    public Response getDeletedUser(int id) {
       return given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                when().get(url+"/"+id);


    }
}
