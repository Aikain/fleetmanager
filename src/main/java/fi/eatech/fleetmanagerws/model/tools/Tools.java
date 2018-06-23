package fi.eatech.fleetmanagerws.model.tools;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Tools class for static tool methods
 *
 * @author Ville Nupponen
 * @since 0.0.1-SNAPSHOT
 */
public class Tools {

    public static String getEnv(String name) {
        String env = System.getenv(name);
        if (env == null || env.isEmpty()) throw new RuntimeException("Set " + name + " -env!");
        return env;
    }

    public static String getEnv(String name, String defaultReturn) {
        try {
            return getEnv(name);
        } catch (RuntimeException ignored) {}
        return defaultReturn;
    }

    public static String formatDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static CarFilter convertJsonToCarFilter(String json) {
        CarFilter carFilter = null;
        try {
            carFilter = new ObjectMapper().readValue(json, CarFilter.class);
        } catch (Exception ignored) {}
        return carFilter;
    }
}
