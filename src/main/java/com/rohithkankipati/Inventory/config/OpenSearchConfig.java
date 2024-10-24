package com.rohithkankipati.Inventory.config;

import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.util.Base64;

@Configuration
public class OpenSearchConfig {

    @Autowired
    private Environment env;

    @Autowired(required = false)
    private ParameterStoreService parameterStoreService;

    @Bean
    public RestHighLevelClient openSearchClient() {
        String username;
        String password;

        // If the active profile is 'prod', fetch secrets from AWS Systems Manager
        if (env.acceptsProfiles(Profiles.of("prod")) && parameterStoreService != null) {
            username = parameterStoreService.getParameter(env.getProperty("aws.ssm.elasticsearch.username"));
            password = parameterStoreService.getParameter(env.getProperty("aws.ssm.elasticsearch.password"));
        } else {
            // Use local credentials for development
            username = env.getProperty("spring.elasticsearch.username");
            password = env.getProperty("spring.elasticsearch.password");
        }

        // Build the Elasticsearch RestHighLevelClient for AWS OpenSearch or local
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(env.getProperty("spring.elasticsearch.uris"), 443, "https")
                ).setDefaultHeaders(new BasicHeader[]{
                        new BasicHeader("Authorization", createBasicAuthHeader(username, password)),
                        new BasicHeader("Content-Type", "application/json") // Set Content-Type to application/json for compatibility
                })
        );
    }

    // Bean definition for ElasticsearchRestTemplate to resolve the missing bean issue
    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate(RestHighLevelClient client) {
        return new ElasticsearchRestTemplate(client);
    }

    // Helper method to create the Authorization header for basic auth
    private String createBasicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }
}
