package com.lucaslouca.util;

import java.text.MessageFormat;
import java.util.*;

/**
 * Class loading and holding the contents of com.lucaslouca.trains.properties.
 */
public class LLPropertyFactory {
    private static final Map<String, String> properties = new HashMap<String, String>();

    // Load property file.
    static {
        loadProperties();
    }

    /**
     * Load properties from file.
     */
    private static void loadProperties() {
        try {
            properties.clear();

            Locale defaultLocale = Locale.getDefault();
            ResourceBundle props = ResourceBundle.getBundle("com.lucaslouca.trains", defaultLocale);

            for (Object key : props.keySet()) {
                String keyStr = key.toString();
                properties.put(keyStr, props.getString(keyStr));
            }

        } catch (MissingResourceException e) {
            throw new IllegalArgumentException("trains.properties file not found!");
        }
    }

    /**
     * Disable instantiation.
     */
    private LLPropertyFactory() {
    }

    /**
     * {@code Map<String, String>} holding the contents of 'trains.properties'.
     *
     * @return map holding the contents of 'trains.properties'.
     */
    public static Map<String, String> getProperties() {
        return properties;
    }

    /**
     * Property value for {@code property}.
     *
     * @param property  key of property.
     * @param arguments property arguments.
     * @return property value formatted with the provided argumens.
     * @throws IllegalArgumentException if property does not exist.
     */
    public static String propertyWithArgs(String property, Object... arguments) {
        String propertyValue = getProperties().get(property);

        if (propertyValue == null) {
            throw new IllegalArgumentException("Property for key '" + property + "' not found!");
        } else {
            MessageFormat formatter = new MessageFormat("");
            formatter.applyPattern(propertyValue);
            String output = formatter.format(arguments);

            return output;
        }
    }
}
