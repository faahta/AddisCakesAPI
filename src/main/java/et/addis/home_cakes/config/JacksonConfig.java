package et.addis.home_cakes.config;

import org.n52.jackson.datatype.jts.JtsModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Fassil on 26/01/22.
 */
@Configuration
public class JacksonConfig {
    @Bean
    public JtsModule jstModule(){
        return new JtsModule();
    }
}
