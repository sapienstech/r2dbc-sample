package ofir.sample.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import ofir.sample.integrate.Bim;
import ofir.sample.util.JsonUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class JsonToBimConverter implements Converter<String, Bim> {

	@Override
	public Bim convert(String string) {
		try {
			return JsonUtil.toObject(string, Bim.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
