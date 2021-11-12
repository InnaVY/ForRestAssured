import org.testng.annotations.DataProvider;

public class UserDataProvider {

    @DataProvider(name = "All fields")
    public static Object[][] allFields() {
        return new Object[][]{
                {"name", "int", "is invalid"}, {"email", "int", "is invalid"},
                {"gender", "int", "is invalid"}, {"status", "int", "is invalid"},
                {"name", "null", "can't be blank"}, {"email", "null", "can't be blank"},
                {"gender", "null", "can't be blank"}, {"status", "null", "can't be blank"},
                {"email", "wrongValue", "is invalid"}, {"gender", "wrongValue", "is invalid"},
                {"status", "wrongValue", "is invalid"}
        };
    }

    @DataProvider(name = "Name and Email fields")
    public  Object[][] nameEmail(){
        return new Object[][] {{"name"}, {"email"} };
    }

    @DataProvider (name = "about token")
    public Object[][] aboutToken(){
        return new Object[][]{{"absent"}, {"invalid"}};
    }

    @DataProvider(name = "Update or delete")
    public Object[][] methodChoose(){
        return new Object[][] {{"update"},{"delete"}};
    }
}
