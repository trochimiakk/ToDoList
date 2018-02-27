package first.spring.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({HibernateConfig.class, WebSecurityConfig.class, MethodSecurityConfig.class})
public class RootConfig {

}
