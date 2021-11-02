import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;

public class UserGRUDOperations {
    public static Response createUser(User user, String token, String uri){
        return given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                header("Authorization", "Bearer "+token).
                body(user.toJString()).
                when().
                post(uri);

    }
    public static void assertionOfUserCreation(Response userCreation, User user){
        userCreation.
                then().
                statusCode(SC_CREATED).
                assertThat().body("data.name", Matchers.equalTo(user.getName())).
                assertThat().body("data.email", Matchers.equalTo(user.getEmail())).
                assertThat().body("data.gender", Matchers.equalTo(user.getGender())).
                assertThat().body("data.status", Matchers.equalTo(user.getStatus())).
                log().
                all();
    }
    public static void updateUserAndAssert(User userUpdated, int id, String token, String uri){
         given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                header("Authorization", "Bearer "+token).
                body(userUpdated.toJStringUpdate()).
                when().
                put(uri+"/"+id).
                then().
                 statusCode(SC_OK).
                 assertThat().body("data.name", Matchers.equalTo(userUpdated.getName())).
                 assertThat().body("data.email", Matchers.equalTo(userUpdated.getEmail())).
                 assertThat().body("data.status", Matchers.equalTo(userUpdated.getStatus())).
                 log().
                 all();
    }
    public static void deleteUserAndAssert(int id, String token, String uri){
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
    }

    public static void getDeletedUserAndAssert(int id, String uri) {
       given().
                header("Content-Type", "application/json").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                when().get(uri+"/"+id)

        .then()
                .statusCode(SC_NOT_FOUND)
                .log().all();
    }
}
