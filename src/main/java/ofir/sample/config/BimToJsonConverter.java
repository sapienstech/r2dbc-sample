package ofir.sample.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import ofir.sample.integrate.Bim;
import ofir.sample.util.JsonUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class BimToJsonConverter implements Converter<Bim, String> {

	@Override
	public String convert(Bim stringObjectMap) {
		try {
			return JsonUtil.toJsonString(stringObjectMap);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
