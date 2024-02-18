package net.unir.missi.desarrollowebfullstack.bookabook.config.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.erhlc.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.client.erhlc.RestClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "net.unir.missi.desarrollowebfullstack.bookabook.repository")
@ComponentScan(basePackages = { "net.unir.missi.desarrollowebfullstack.bookabook.service" })
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();

        return RestClients.create(clientConfiguration)
                .rest();
    }
}