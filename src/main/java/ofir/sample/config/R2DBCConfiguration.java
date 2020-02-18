package ofir.sample.config;

import com.google.common.collect.Lists;
import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@Configuration
@EnableR2dbcRepositories(basePackages = "ofir.sample.repository")
@EnableTransactionManagement
public class R2DBCConfiguration extends AbstractR2dbcConfiguration {
	@Bean
	public H2ConnectionFactory connectionFactory() {
		return new H2ConnectionFactory(
				H2ConnectionConfiguration.builder()
										 .url("mem:testdb;DB_CLOSE_DELAY=-1;")
										 //.url("mem:testdb;DB_CLOSE_DELAY=-1;TRACE_LEVEL_FILE=4")
										 .username("sa")
										 .build());
	}

	@Bean
	@Override
	public R2dbcCustomConversions r2dbcCustomConversions() {
		List<Converter<?, ?>> converterList = Lists.newArrayList(new JsonToMapConverter(),
																 new MapToJsonConverter(),
																 new BimToJsonConverter(),
																 new JsonToBimConverter());
		return new R2dbcCustomConversions(getStoreConversions(), converterList);
	}
}
