import org.testng.annotations.DataProvider;

public class UserDataProvider {

    @DataProvider(name = "All fields")
    public static Object[][] allFields() {
        return new Object[][]{
                {"name", "int"}, {"email", "int"},
                {"gender", "int"}, {"status", "int"},
                {"name", "null"}, {"email", "null"},
                {"gender", "null"}, {"status", "null"},
                {"email", "wrongValue"}, {"gender", "wrongValue"},
                {"status", "wrongValue"}
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
