package rnd.dev.s3storagelearning.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

import static rnd.dev.s3storagelearning.constant.CredentialConstant.*;

@Configuration
public class S3Config {

    @Bean
    public S3Client getS3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create(LOCAL_AWS_ENDPOINT))
                .region(Region.US_EAST_1)
                .forcePathStyle(true) // Add this line
                .build();
    }
}
