package com.rohithkankipati.Inventory.config;

import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
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

    // Create RestHighLevelClient bean for backward compatibility with Spring Data Elasticsearch 7.x
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

        RestClientBuilder builder = RestClient.builder(
                new HttpHost(env.getProperty("spring.elasticsearch.uris"), 443, "https")
        ).setDefaultHeaders(new BasicHeader[]{
                new BasicHeader("Authorization", createBasicAuthHeader(username, password)),
                new BasicHeader("Content-Type", "application/json") // Set Content-Type to application/json for compatibility
        });

        return new RestHighLevelClient(builder);
    }

    // Create ElasticsearchRestTemplate bean to replace deprecated ElasticsearchTemplate
    @Bean
    public ElasticsearchRestTemplate elasticsearchRestTemplate() {
        return new ElasticsearchRestTemplate(openSearchClient());
    }

    // Helper method to create the Authorization header for basic auth
    private String createBasicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }
}
