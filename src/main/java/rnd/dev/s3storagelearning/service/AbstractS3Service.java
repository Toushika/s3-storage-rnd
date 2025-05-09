package rnd.dev.s3storagelearning.service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AbstractS3Service {
    private final S3Client s3Client;

    public String createS3Bucket(String bucketName) {
        CreateBucketResponse createBucketResponse = s3Client.createBucket(CreateBucketRequest.builder()
                .bucket(bucketName)
                .build());
        return createBucketResponse.location();

    }

    public String getS3Object(String bucketName, String key) {
        try {
            GetObjectRequest request = getGetObjectRequest(bucketName, key);

            try (ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(request);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }

        } catch (S3Exception e) {
            System.err.println("S3 getObject failed: " + e.awsErrorDetails().errorMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error reading S3 object: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public DeleteObjectResponse deleteObjectFromS3(String bucketName, String key) {
        try {
            DeleteObjectRequest deleteRequest = getDeleteObjectRequest(bucketName, key);

            return s3Client.deleteObject(deleteRequest);
        } catch (S3Exception e) {
            System.err.println("Failed to delete object: " + e.awsErrorDetails().errorMessage());
            throw e;
        }

    }

    public List<String> getFiles(String bucketName) {
        ListObjectsResponse listObjectsResponse = s3Client.listObjects(ListObjectsRequest.builder()
                .bucket(bucketName)
                .build());

        return listObjectsResponse.contents().stream().map(S3Object::key).collect(Collectors.toList());
    }

//    public List<S3Object> getS3ObjectList(String bucketName, String key) {
//        try {
//            ListObjectsV2Request listRequest = getListObjectsV2Request(bucketName, key);
//
//            ListObjectsV2Response listResponse = s3Client.listObjectsV2(listRequest);
//
//            return listResponse.contents(); // returns list of S3Object
//        } catch (S3Exception e) {
//            System.err.println("Failed to list objects: " + e.awsErrorDetails().errorMessage());
//            throw e;
//        }
//    }

    public List<String> getBucketList() {
        return s3Client.listBuckets().buckets()
                .stream()
                .map(Bucket::name)
                .collect(Collectors.toList());
    }

    public boolean deleteBucket(String bucketName) {
        return s3Client.deleteBucket(DeleteBucketRequest.builder()
                        .bucket(bucketName)
                        .build())
                .sdkHttpResponse()
                .isSuccessful();
    }

    private PutObjectRequest getPutObjectRequest(String bucket, String key, String contentType) {
        return PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .build();
    }

    private GetObjectRequest getGetObjectRequest(String bucketName, String keyStr) {
        return GetObjectRequest.builder()
                .bucket(bucketName)
                .key(keyStr)
                .build();
    }

    private ListObjectsV2Request getListObjectsV2Request(String bucketName, String key) {
        return ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(key)
                .build();
    }

    private DeleteObjectRequest getDeleteObjectRequest(String bucketName, String key) {
        return DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

    }

    //    public PutObjectResponse uploadS3Object(String bucket, String key, String content) {
//        try {
//            PutObjectRequest request = getPutObjectRequest(bucket, key, TEXT_CONTENT_TYPE);
//
//            return s3Client.putObject(request, RequestBody.fromBytes(content.getBytes(StandardCharsets.UTF_8)));
//        } catch (S3Exception e) {
//            System.err.println("S3 upload failed: " + e.awsErrorDetails().errorMessage());
//            throw e;
//        }
//    }
    public PutObjectResponse uploadS3Object(String bucket, String key, byte[] bytes, String contentType) {
        try {

            PutObjectRequest request = getPutObjectRequest(bucket, key, contentType);

            return s3Client.putObject(request, RequestBody.fromBytes(bytes));
        } catch (S3Exception e) {
            System.err.println("S3 upload failed: " + e.awsErrorDetails().errorMessage());
            throw e;
        }
    }
}
