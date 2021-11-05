package property.manager;

import java.io.*;
import java.util.Properties;

public class PropertyManager {

private static String resourceFilePath;

    public static void setProperties() {
        // creating String that is path to properties file
        String workingDirectory = System.getProperty("user.dir");
        resourceFilePath = workingDirectory + "/src/test/resources/TestCsv/" +
                "config.properties";
        try (OutputStream outputFile = new FileOutputStream(resourceFilePath)) {

            Properties properties = new Properties();
            // set the properties value
            properties.setProperty("url", "https://gorest.co.in/public/v1/users");
            properties.setProperty("token", "6e398124a1dd58b3e41e21ec5e41a269137a8771586f4fc839091bcc99606ed3");

            // save properties to properties file
            properties.store(outputFile, "token and url");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getProperty(String propertyName){

            try (InputStream input = new FileInputStream(resourceFilePath)) {

                Properties properties = new Properties();

                // load a properties file
                properties.load(input);

                // get the property value and return it
                return properties.getProperty(propertyName);

            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }

    }
    }




