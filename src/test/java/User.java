import com.github.javafaker.Faker;
import org.json.JSONObject;
import java.util.Locale;

public class User {
    private String name;
    private String email;
    private String gender;
    private String status;

    //the following constructor is for request object
    public User(){
        Faker faker = new Faker(new Locale("en-GB"));
        this.name = faker.name().fullName();
        this.email = faker.internet().emailAddress();
        this.gender = faker.random().nextBoolean()?"male":"female";
        this.status = "active";
    }

    //the following constructor is for response object
    public User(JSONObject responseObj){
        this.name = responseObj.getJSONObject("data").getString("name");
        this.email = responseObj.getJSONObject("data").getString("email");
        this.gender = responseObj.getJSONObject("data").getString("gender");
        this.status = responseObj.getJSONObject("data").getString("status");
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public String getGender() {
        return gender;
    }

    public String getStatus() {
        return status;
    }

    public String toJString(){
        return "{\"name\": \""+this.name+"\"," +
                "\"email\": \""+this.email+"\"," +
                "\"gender\": \""+this.gender+"\"," +
                "\"status\": \""+this.status+"\"}";
    }

}
