package cn.com.utils;

import java.io.InputStream;
import java.util.Properties;

public class PropUtils {

    private Properties properties = null;

    public PropUtils(){
        try {
            InputStream inputStream = PropUtils.class.getClassLoader().getResourceAsStream("constans.properties");
            properties = new Properties();
            properties.load(inputStream);
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getValueByKey(String key){
        try{
            return properties.getProperty(key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

}
