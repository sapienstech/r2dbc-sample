package ofir.sample.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import ofir.sample.util.JsonUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Map;

@ReadingConverter
public class JsonToMapConverter implements Converter<String, Map<String, Object>> {

	@Override
	public Map<String, Object> convert(String string) {
		try {
			return JsonUtil.toObject(string, Map.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
