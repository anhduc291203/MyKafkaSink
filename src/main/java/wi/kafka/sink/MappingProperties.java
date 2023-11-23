package wi.kafka.sink;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class MappingProperties
{

    private Properties properties;

    private static final String DEFAULT_CONFIG_FILE = "config.properties";
    public MappingProperties(){
        this(DEFAULT_CONFIG_FILE);
    }
    public MappingProperties(String filename){
        properties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = MappingProperties.class.getClassLoader()
                    .getResourceAsStream(filename);

            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

    public static void main(String args[]){
        MappingProperties mappingProperties = new MappingProperties();

        String idPropertyValue = mappingProperties.getProperty("id");

        if (idPropertyValue != null) {
            System.out.println("Value for key 'id': " + idPropertyValue);
        } else {
            System.out.println("Key 'id' not found or value is null.");
        }
    }
}
