package com.rohithkankipati.Inventory.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.opensearch.OpenSearchClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;

import java.net.URI;

@Configuration
public class OpenSearchConfig {

    @Autowired
    private Environment env;

    @Autowired(required = false)
    private ParameterStoreService parameterStoreService;

    @Bean
    public OpenSearchClient openSearchClient() {
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

        // Build the OpenSearch client using credentials and endpoint
        OpenSearchClient client = OpenSearchClient.builder()
                .region(Region.of("us-east-1")) // Set your AWS region
                .endpointOverride(URI.create(env.getProperty("spring.elasticsearch.uris"))) 
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(username, password)
                ))
                .httpClient(ApacheHttpClient.builder().build()) 
                .build();

        return client;
    }
}



