package rnd.dev.s3storagelearning.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rnd.dev.s3storagelearning.record.bucket.BucketRequest;
import rnd.dev.s3storagelearning.record.bucket.BucketResponse;
import rnd.dev.s3storagelearning.record.file.FileResponse;
import rnd.dev.s3storagelearning.service.S3Service;

import java.util.List;

@Slf4j
@RestController
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/create/bucket")
    public BucketResponse createBucket(@RequestBody BucketRequest bucketRequest) {
        return s3Service.addBucket(bucketRequest);
    }

    @GetMapping("/get/bucket")
    public List<String> createBucket() {
        return s3Service.getBuckets();
    }

    @DeleteMapping("/delete/bucket")
    public BucketResponse deleteBucket(@RequestBody BucketRequest bucketRequest) {
        return s3Service.deleteBucket(bucketRequest);
    }

    @PostMapping("/add/file")
    public FileResponse addFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("bucketName") String bucketName) {
        return s3Service.addFile(multipartFile, bucketName);
    }

}
