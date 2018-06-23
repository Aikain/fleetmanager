package fi.eatech.fleetmanagerws.model.tools;

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

}
