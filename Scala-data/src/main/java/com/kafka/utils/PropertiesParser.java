package com.kafka.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author JohnLiu
 * @version 0.1.0
 * @date 2014/8/27
 */
public class PropertiesParser{

    private static final Log log = LogFactory.getLog(PropertiesParser.class);
     /* properties file type */
    Properties props = null;

    /* constructor method*/
    public PropertiesParser(Properties props) {
        this.props = props;
    }
    /**
     * Get the trimmed String value of the property with the given
     * <code>name</code>.  If the value the empty String (after
     * trimming), then it returns null.
     */
    public String getStringProperty(String name) {
        return getStringProperty(name, null);
    }

    /**
     * Get the trimmed String value of the property with the given
     * <code>name</code> or the given default value if the value is
     * null or empty after trimming.
     */
    public String getStringProperty(String name, String def) {
        String val = props.getProperty(name, def);
        if (val == null) {
            return def;
        }

        val = val.trim();

        return (val.length() == 0) ? def : val;
    }

    private  Properties loadPropertiesFile() {
        Properties props = new Properties();
        InputStream in;
        ClassLoader cl = getClass().getClassLoader();
        if(cl == null)
            cl = findClassloader();
        if(cl == null)
            try {
                throw new ProcessingException("Unable to find a class loader on the current thread or class.");
            } catch (ProcessingException e) {
                e.printStackTrace();
            }
        in = cl.getResourceAsStream(PropertiesSettings.CONSUMER_FILE_NAME);
        try {
            props.load(in);
        } catch (IOException ioe) {
            log.error("can't load "+ PropertiesSettings.CONSUMER_FILE_NAME, ioe);
        }
        return props;
    }

    private ClassLoader findClassloader() {
        // work-around set context loader for windows-service started jvms (QUARTZ-748)
        if(Thread.currentThread().getContextClassLoader() == null && getClass().getClassLoader() != null) {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        }
        return Thread.currentThread().getContextClassLoader();
    }

    public static Properties getProperties(final String fileName){
        Properties props = new Properties();
        InputStream in = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(fileName);
        try {
            props.load(in);
        } catch (IOException ioe) {
            log.error("can't load "+fileName, ioe);
        }
        return props;
    }
}
