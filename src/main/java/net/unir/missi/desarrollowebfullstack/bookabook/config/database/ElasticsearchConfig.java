package net.unir.missi.desarrollowebfullstack.bookabook.config.database;

import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.net.ssl.SSLContext;
import java.security.KeyStore;

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
                .usingSsl(ElasticsearchConfig.buildSSLContext())
                .withBasicAuth("elatic", "chemistry")
                .build();
    }

    /**
     * Builds a dummy SSL context that trust all peers.
     *
     * @return Instance of SSLContext that trusts everyone.
     */
    private static SSLContext buildSSLContext()
    {
        try {
            return new SSLContextBuilder().loadTrustMaterial((KeyStore) null, (TrustStrategy) TrustAllStrategy.INSTANCE).build();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}