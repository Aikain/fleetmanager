package fi.eatech.fleetmanagerws;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * .
 *
 * @author Ville Nupponen
 * @since .
 */
public class TestUtils {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private static ObjectMapper mapper = new ObjectMapper() {{
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }};

    public static String convertObjectToJsonString(Object object) throws IOException {
        return mapper.writeValueAsString(object);
    }

    public static <T extends Object> T convertJsonStringToObject(String json, TypeReference typeReference) throws IOException {
        return mapper.readValue(json, typeReference);
    }
}
