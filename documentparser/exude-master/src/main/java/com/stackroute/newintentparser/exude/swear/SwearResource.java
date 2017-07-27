
package com.stackroute.newintentparser.exude.swear;

import java.io.InputStream;
import java.util.Properties;

import com.stackroute.newintentparser.exude.common.BaseResource;
import com.stackroute.newintentparser.exude.common.Constants;

public class SwearResource implements BaseResource {

    private static Properties properties = null;

    private void loadResource() {
        try {
            if (properties == null) {
                properties = new Properties();
                InputStream input = getClass().getClassLoader().getResourceAsStream(Constants.Resources.SWEAR_EN_FILE);
                if (input != null) {
                    properties.load(input);
                } else {
                    System.err.println("Swear word resource file not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getProperties(String property) {
        if (properties == null) {
            loadResource();
        }
        return properties.getProperty(property);
    }

}
