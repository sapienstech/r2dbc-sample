package ofir.sample.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import ofir.sample.util.JsonUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.util.Map;

@WritingConverter
public class MapToJsonConverter implements Converter<Map<String, Object>, String> {

	@Override
	public String convert(Map<String, Object> stringObjectMap) {
		try {
			return JsonUtil.toJsonString(stringObjectMap);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
