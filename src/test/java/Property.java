import java.util.*;

public class Property {
   private Properties properties = new Properties();

    public Property(){
        properties.setProperty("url","https://gorest.co.in/public/v1/users");
        properties.setProperty("token", "6e398124a1dd58b3e41e21ec5e41a269137a8771586f4fc839091bcc99606ed3");
    }

     public Properties getProperties(){
        return properties;
     }
}
