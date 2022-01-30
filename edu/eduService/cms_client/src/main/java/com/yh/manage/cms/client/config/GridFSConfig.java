package com.yh.manage.cms.client.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GridFSConfig {
    @Value("${spring.data.mongodb.database}")
    private String mongodb;

    @Bean
    public GridFSBucket getGridFSBucket(){
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase(mongodb);
        GridFSBucket gridFSBucket = GridFSBuckets.create(database);
        return gridFSBucket;
    }
}
