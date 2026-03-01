package config;

import java.io.InputStream;
import java.util.Properties;

public class AdminCredentials {

    private static Properties props = new Properties();

    static {
        try (InputStream is = AdminCredentials.class.getResourceAsStream("/application.properties")) {
            if (is != null) props.load(is);
        } catch (Exception ignore) { }
    }

    public static String login() {
        return props.getProperty("admin.login");
    }

    public static String password() {
        return props.getProperty("admin.password");
    }
}
