package com.example.springbootdemo.config;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * 参考
 * https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/7.17/index.html
 * */
@EnableElasticsearchRepositories
@Configuration
public class EsConfig {
    //Elastic client
    @Bean
    public RestClient restClient() {
        // Create the low-level client
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("localhost", 9200)
        );
        //https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/7.17/java-rest-low-usage-initialization.html
        builder.setFailureListener(new RestClient.FailureListener() {
            @Override
            public void onFailure(Node node) {

            }
        });
        builder.setRequestConfigCallback(
                new RestClientBuilder.RequestConfigCallback() {
                    @Override
                    public RequestConfig.Builder customizeRequestConfig(
                            RequestConfig.Builder requestConfigBuilder) {
                        return requestConfigBuilder.setSocketTimeout(10000);
                    }
                });
        return builder.build();
    }

    @Bean
    public ElasticsearchTransport elasticsearchTransport(RestClient restClient) {
        // Create the Java API Client with the same low level client
        ElasticsearchTransport transport = new RestClientTransport(
                restClient,
                new JacksonJsonpMapper()//JSON
        );
        return transport;
    }

    @Bean
    public ElasticsearchClient elasticsearchClient(ElasticsearchTransport transport) {
        // Blocking client
        return new ElasticsearchClient(transport);//co.elastic.clients.elasticsearch.ElasticsearchClient
    }

    @Bean
    public ElasticsearchAsyncClient elasticsearchAsyncClient(ElasticsearchTransport transport) {
        // Asynchronous non-blocking client
        ElasticsearchAsyncClient asyncClient =
                new ElasticsearchAsyncClient(transport);
        return asyncClient;
    }

    //Spring
    @Bean
    public RestHighLevelClient restHighLevelClient(RestClient restClient) {
        // Create the HLRC
        return new RestHighLevelClientBuilder(restClient)
                .setApiCompatibilityMode(true)
                .build();
    }

    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate(RestHighLevelClient restHighLevelClient) {
        return new ElasticsearchRestTemplate(restHighLevelClient);
    }

}
