package rnd.dev.s3storagelearning.service;

import org.springframework.web.multipart.MultipartFile;
import rnd.dev.s3storagelearning.record.file.DeleteFileRequest;
import rnd.dev.s3storagelearning.record.file.FileRequest;
import rnd.dev.s3storagelearning.record.file.FileResponse;
import rnd.dev.s3storagelearning.record.request.CreateBucketRequest;
import rnd.dev.s3storagelearning.record.response.CreateBucketResponse;

import java.util.List;

public interface S3Service {
    CreateBucketResponse addBucket(CreateBucketRequest bucketRequest);

    List<String> getBuckets();

    CreateBucketResponse deleteBucket(CreateBucketRequest bucketRequest);

    FileResponse addFile(MultipartFile multipartFile, String bucketName);

    List<String> viewFiles(FileRequest fileRequest);

    FileResponse deleteFile(DeleteFileRequest fileRequest);
}
