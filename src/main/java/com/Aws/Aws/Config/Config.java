package com.Aws.Aws.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.Data;

@Configuration
public @Data class Config {

    private ConfigData configData;
	
    @Bean
	public AmazonS3 s3Client() {
		AWSCredentials credentials = new BasicAWSCredentials(configData.accessKey,configData.accessSecret);
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(configData.region)
				.build();
	}
	
}
