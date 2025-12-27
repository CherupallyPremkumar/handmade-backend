package com.handmade.ecommerce.search.elasticsearch.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.io.IOException;

/**
 * Elasticsearch configuration.
 * Configures Elasticsearch REST client with connection settings.
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "search.elasticsearch")
@Data
public class ElasticsearchConfiguration {
    
    private String hosts = "localhost:9200";
    private String username;
    private String password;
    private String indexPrefix = "handmade";
    private int connectionTimeout = 5000;
    private int socketTimeout = 60000;
    
    private RestHighLevelClient client;
    
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        log.info("Initializing Elasticsearch client with hosts: {}", hosts);
        
        // Parse hosts
        String[] hostArray = hosts.split(",");
        HttpHost[] httpHosts = new HttpHost[hostArray.length];
        
        for (int i = 0; i < hostArray.length; i++) {
            String[] parts = hostArray[i].trim().split(":");
            String host = parts[0];
            int port = parts.length > 1 ? Integer.parseInt(parts[1]) : 9200;
            httpHosts[i] = new HttpHost(host, port, "http");
        }
        
        // Build client
        RestClientBuilder builder = RestClient.builder(httpHosts)
            .setRequestConfigCallback(requestConfigBuilder ->
                requestConfigBuilder
                    .setConnectTimeout(connectionTimeout)
                    .setSocketTimeout(socketTimeout));
        
        // Add authentication if provided
        if (username != null && !username.isEmpty()) {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));
            
            builder.setHttpClientConfigCallback(httpClientBuilder ->
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        }
        
        client = new RestHighLevelClient(builder);
        log.info("Elasticsearch client initialized successfully");
        
        return client;
    }
    
    @PreDestroy
    public void cleanup() {
        if (client != null) {
            try {
                log.info("Closing Elasticsearch client");
                client.close();
            } catch (IOException e) {
                log.error("Error closing Elasticsearch client", e);
            }
        }
    }
    
    /**
     * Get full index name with prefix
     */
    public String getIndexName(String entityType) {
        return indexPrefix + "-" + entityType.toLowerCase();
    }
}
