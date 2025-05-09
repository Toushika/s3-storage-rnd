package rnd.dev.s3storagelearning.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rnd.dev.s3storagelearning.record.request.DeleteFileRequest;
import rnd.dev.s3storagelearning.record.request.FileRequest;
import rnd.dev.s3storagelearning.record.response.FileResponse;
import rnd.dev.s3storagelearning.record.request.CreateBucketRequest;
import rnd.dev.s3storagelearning.record.request.DeleteBucketRequest;
import rnd.dev.s3storagelearning.record.response.CreateBucketResponse;
import rnd.dev.s3storagelearning.record.response.DeleteBucketResponse;
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
    public CreateBucketResponse createBucket(@RequestBody CreateBucketRequest bucketRequest) {
        return s3Service.addBucket(bucketRequest);
    }

    @GetMapping("/get/bucket")
    public List<String> createBucket() {
        return s3Service.getBuckets();
    }

    @DeleteMapping("/delete/bucket")
    public DeleteBucketResponse deleteBucket(@RequestBody DeleteBucketRequest deleteBucketRequest) {
        return s3Service.deleteBucket(deleteBucketRequest);
    }

    @PostMapping("/add/file")
    public FileResponse addFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("bucketName") String bucketName) {
        return s3Service.addFile(multipartFile, bucketName);
    }

    @PostMapping("/view/files")
    public List<String> viewFiles(@RequestBody FileRequest fileRequest) {
        return s3Service.viewFiles(fileRequest);
    }

    @DeleteMapping("/delete/file")
    public FileResponse deleteFile(@RequestBody DeleteFileRequest deleteFileRequest) {
        return s3Service.deleteFile(deleteFileRequest);
    }

}
