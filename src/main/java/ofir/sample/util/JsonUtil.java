package ofir.sample.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/***
 * Util to consolidate JSON related operations to be done in the same expected format
 * and to re-use same object mapper
 */
public class JsonUtil {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	public static <T> String toJsonString(T object) throws JsonProcessingException {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (final JsonProcessingException e) {
			logger.error("JSON writing error for object of class \"" + object.getClass().getName() + "\"", e);
			throw e;
		}
	}

	public static <T> T toObject(String jsonString, Class<T> clazz) throws JsonProcessingException {
		try {
			return objectMapper.readValue(jsonString, clazz);
		} catch (final IOException e) {
			logger.error("JSON reading error for string \"" + jsonString + "\" and class \"" + clazz.getName() + "\"", e);
			throw e;
		}
	}

	public static Map<String, Object> toMap(String jsonString) throws JsonProcessingException{
		try {
			return toObject(jsonString, Map.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static JsonNode toJsonTreeRoot(String jsonString) throws JsonProcessingException {
		try {
			return objectMapper.readTree(jsonString);
		} catch (JsonProcessingException e) {
			logger.error("JSON reading error for string \"" + jsonString + "\"", e);
			throw e;
		}
	}
}
