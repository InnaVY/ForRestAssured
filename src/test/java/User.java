import com.github.javafaker.Faker;
import org.json.JSONObject;
import java.util.Locale;

public class User {

    private UserJsonForm userJsonForm;
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
        this.status = faker.random().nextBoolean()?"active":"inactive";
        this.userJsonForm = new UserJsonForm(this);
    }
   public User(User user){
       Faker faker = new Faker(new Locale("en-GB"));
       this.email = faker.internet().emailAddress();
       this.name = faker.name().fullName();
       this.status = faker.random().nextBoolean()?"active":"inactive";
       this.userJsonForm = new UserJsonForm(this);
   }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setField(String field, String value){
        if (value.equals("word")) {
            String word = RandomCreation.createRandomWord();
            switch (field) {
                case "name":
                    this.setName(word);
                    break;
                case "email":
                    this.setEmail(word);
                    break;
                case "gender":
                    this.setGender(word);
                    break;
                case "status":
                    this.setStatus(word);
                    break;
            }
        }
        else {
                switch (field) {
                    case "name":
                        this.setName(null);
                        break;
                    case "email":
                        this.setEmail(null);
                        break;
                    case "gender":
                        this.setGender(null);
                        break;
                    case "status":
                        this.setStatus(null);
                        break;
                }
            }
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

    public UserJsonForm getUserJsonForm() {
        return userJsonForm;
    }
    public String toJString(){
        return userJsonForm.toJStringNew();
    }
    public String toJStringUpdate(){
        return userJsonForm.toJStringUpdate();
    }

}
