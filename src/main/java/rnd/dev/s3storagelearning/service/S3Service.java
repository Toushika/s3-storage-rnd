package rnd.dev.s3storagelearning.service;

import org.springframework.web.multipart.MultipartFile;
import rnd.dev.s3storagelearning.record.bucket.BucketRequest;
import rnd.dev.s3storagelearning.record.bucket.BucketResponse;
import rnd.dev.s3storagelearning.record.file.FileResponse;

import java.util.List;

public interface S3Service {
    BucketResponse addBucket(BucketRequest bucketRequest);

    List<String> getBuckets();

    BucketResponse deleteBucket(BucketRequest bucketRequest);

    FileResponse addFile(MultipartFile multipartFile, String bucketName);
}
