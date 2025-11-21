package io.fouad.backend.tests;

import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources({
    @PropertySource("classpath:application.properties"),
    @PropertySource("classpath:application-test.properties")
})
@TestConfiguration(proxyBeanMethods = false)
public class TestRunConfig {
    
    @Bean
    @ServiceConnection("mssql")
    @RestartScope
    public MssqlServerContainer mssqlServerContainer() {
        return new MssqlServerContainer();
    }
}