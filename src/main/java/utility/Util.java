package utility;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

public class Util {

    public static InputStream loadFromResource(String fileName) {
        InputStream inputStream = Util.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            String msg = "Resource not found: " + fileName;
            LoggerUtil.warn(msg);
            throw new IllegalArgumentException(msg);
        }
        return inputStream;
    }

    private static final Properties props = new Properties();

    static {
        try (InputStream input = Util.loadFromResource("Config.properties")) {
            props.load(input);
        } catch (IOException e) {
            String msg = "Failed to load Config.properties";
            LoggerUtil.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    /**
     To prevent reloads when each time getConfig() is triggered
     */
    private static String getConfig(String target) {
        return props.getProperty(target);
    }

    public static String getAvatar() {
        return getConfig("avatar_source");
    }

    public static String getWebsite() {
        return getConfig("demo_website");
    }

    public static Boolean getHeadless() {
        String config = getConfig("headless");
        if (config == null) {
            return false; // fallback if not set
        }
        return config.equalsIgnoreCase("true");
    }

    /**
     * if user_count is minus # or NaN, the fallback value will be 1.
     * Thus, the script will at least generate 1 entry
     * */
    public static Integer getUserCount() {
        try {
            int count = Integer.parseInt(getConfig("user_count").trim());
            return count > 0 ? count : 1;
        } catch (Exception e) {
            LoggerUtil.warn("Using fallback value");
            return 1;
        }
    }

    /**
     * default to /output at project root (same level as /src)
     * */
    public static String getOutputPath() {
        String path = getConfig("output_path");
        if (path == null || path.isBlank()) {
            path = Paths.get(System.getProperty("user.dir"), "output").toString();
        }
        return path;
    }

}
