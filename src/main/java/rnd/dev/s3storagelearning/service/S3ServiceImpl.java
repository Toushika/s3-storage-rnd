package rnd.dev.s3storagelearning.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import rnd.dev.s3storagelearning.record.bucket.BucketRequest;
import rnd.dev.s3storagelearning.record.bucket.BucketResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3ServiceImpl implements S3Service {
    private final S3Client s3Client;

    public S3ServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public BucketResponse addBucket(@RequestBody BucketRequest bucketRequest) {
        CreateBucketResponse bucket = s3Client.createBucket(CreateBucketRequest.builder()
                .bucket(bucketRequest.getBucketName())
                .build());
        BucketResponse bucketResponse = BucketResponse
                .builder()
                .bucketName(bucketRequest.getBucketName())
                .location(bucket.location())
                .status(BucketResponse.Status.CREATED)
                .build();

        return bucketResponse;
    }

    @Override
    public List<String> getBuckets() {
        ListBucketsResponse listBucketsResponse = s3Client.listBuckets();
        return listBucketsResponse.buckets().stream()
                .map(Bucket::name)
                .collect(Collectors.toList());
    }

    @Override
    public BucketResponse deleteBucket(@RequestBody BucketRequest bucketRequest) {
        DeleteBucketResponse bucket = s3Client.deleteBucket(DeleteBucketRequest.builder()
                .bucket(bucketRequest.getBucketName())
                .build());
        BucketResponse bucketResponse = BucketResponse
                .builder()
                .bucketName(bucketRequest.getBucketName())
                .status(BucketResponse.Status.DELETED)
                .build();
        return bucketResponse;

    }
}
