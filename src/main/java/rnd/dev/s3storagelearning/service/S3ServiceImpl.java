package rnd.dev.s3storagelearning.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import rnd.dev.s3storagelearning.record.bucket.BucketRequest;
import rnd.dev.s3storagelearning.record.bucket.BucketResponse;
import rnd.dev.s3storagelearning.record.file.DeleteFileRequest;
import rnd.dev.s3storagelearning.record.file.FileRequest;
import rnd.dev.s3storagelearning.record.file.FileResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
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

    @Override
    public FileResponse addFile(MultipartFile multipartFile, String bucketName) {
        // Build the request
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(multipartFile.getOriginalFilename())
                .contentType(multipartFile.getContentType())
                .build();

        try {
            // Upload the file using try-with-resources
            try (InputStream inputStream = multipartFile.getInputStream()) {
                s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromInputStream(inputStream, multipartFile.getSize()));
            }

            // Build the response
            FileResponse fileResponse = FileResponse.builder()
                    .bucketName(putObjectRequest.bucket())
                    .fileName(putObjectRequest.key())
                    .status(FileResponse.Status.CREATED)
                    .build();
            return fileResponse;
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file to S3", e);
        }
    }

    @Override
    public List<String> viewFiles(FileRequest fileRequest) {
        ListObjectsResponse listObjectsResponse = s3Client.listObjects(ListObjectsRequest.builder()
                .bucket(fileRequest.bucketName)
                .build());
        return listObjectsResponse.contents().stream().map(S3Object::key).collect(Collectors.toList());
    }

    @Override
    public FileResponse deleteFile(DeleteFileRequest fileRequest) {
        DeleteObjectResponse deleteObjectResponse = s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(fileRequest.getBucketName())
                .key(fileRequest.getFileName())
                .build());
        return FileResponse.builder()
                .bucketName(fileRequest.getBucketName())
                .fileName(fileRequest.getFileName())
                .status(FileResponse.Status.DELETED)
                .build();
    }
}
