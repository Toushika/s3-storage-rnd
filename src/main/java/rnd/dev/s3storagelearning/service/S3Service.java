package rnd.dev.s3storagelearning.service;

import org.springframework.web.bind.annotation.RequestBody;
import rnd.dev.s3storagelearning.record.bucket.BucketRequest;
import rnd.dev.s3storagelearning.record.bucket.BucketResponse;
import software.amazon.awssdk.services.s3.model.Bucket;

import java.util.List;

public interface S3Service {
    BucketResponse addBucket(@RequestBody BucketRequest bucketRequest);

    List<String> getBuckets();

    BucketResponse deleteBucket(@RequestBody BucketRequest bucketRequest);
}
