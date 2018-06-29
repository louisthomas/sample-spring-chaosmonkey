package pl.piomin.services.order;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.PooledServiceConnectorConfig;
import org.springframework.cloud.service.relational.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("cloud")
public class CloudConfiguration extends AbstractCloudConfig {

    @Value("${datasource.min-pool-size:0}")
    private int minSize;

    @Value("${datasource.max-pool-size:40}")
    private int maxSize;

    @Value("${datasource.max-wait:3000}")
    private int maxWait;

    @Bean
    public DataSource dataSource() {
        PooledServiceConnectorConfig.PoolConfig poolConfig = new PooledServiceConnectorConfig.PoolConfig(minSize, maxSize, maxWait);
        return connectionFactory().dataSource(new DataSourceConfig(poolConfig, null));
    }

}