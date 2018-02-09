package first.spring.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({HibernateConfig.class, WebSecurityConfig.class})
public class RootConfig {

}
