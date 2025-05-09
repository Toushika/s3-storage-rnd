package rnd.dev.s3storagelearning.service;

import org.springframework.web.multipart.MultipartFile;
import rnd.dev.s3storagelearning.record.file.DeleteFileRequest;
import rnd.dev.s3storagelearning.record.file.FileRequest;
import rnd.dev.s3storagelearning.record.response.FileResponse;
import rnd.dev.s3storagelearning.record.request.CreateBucketRequest;
import rnd.dev.s3storagelearning.record.request.DeleteBucketRequest;
import rnd.dev.s3storagelearning.record.response.CreateBucketResponse;
import rnd.dev.s3storagelearning.record.response.DeleteBucketResponse;

import java.util.List;

public interface S3Service {
    CreateBucketResponse addBucket(CreateBucketRequest bucketRequest);

    List<String> getBuckets();

    DeleteBucketResponse deleteBucket(DeleteBucketRequest deleteBucketRequest);

    FileResponse addFile(MultipartFile multipartFile, String bucketName);

    List<String> viewFiles(FileRequest fileRequest);

    FileResponse deleteFile(DeleteFileRequest fileRequest);
}
