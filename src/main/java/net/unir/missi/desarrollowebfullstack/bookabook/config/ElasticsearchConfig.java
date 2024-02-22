package net.unir.missi.desarrollowebfullstack.bookabook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "net.unir.missi.desarrollowebfullstack.bookabook.repository")
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    /**
     * Configures the ElasticSearch client
     *
     * @return Client instance to connect to our Elastic instance.
     */
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedToLocalhost()
                .build();
    }
}