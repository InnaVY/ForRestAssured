import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Properties;

import static io.restassured.RestAssured.given;


public class UserRequestPayload {

    private Property property;
    private Properties p;
    private String token;
    private String url;

    public UserRequestPayload(){
        this.property= new Property();
        this.p = property.getProperties();
        this.token = p.getProperty("token");
        this.url = p.getProperty("url");
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
