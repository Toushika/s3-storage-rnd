package rnd.dev.s3storagelearning.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import rnd.dev.s3storagelearning.constant.enums.BucketStatus;
import rnd.dev.s3storagelearning.constant.enums.FileStatus;
import rnd.dev.s3storagelearning.record.file.DeleteFileRequest;
import rnd.dev.s3storagelearning.record.file.FileRequest;
import rnd.dev.s3storagelearning.record.request.CreateBucketRequest;
import rnd.dev.s3storagelearning.record.request.DeleteBucketRequest;
import rnd.dev.s3storagelearning.record.response.CreateBucketResponse;
import rnd.dev.s3storagelearning.record.response.DeleteBucketResponse;
import rnd.dev.s3storagelearning.record.response.FileResponse;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class S3ServiceImpl extends AbstractS3Service implements S3Service {
    private final S3Client s3Client;

    public S3ServiceImpl(S3Client s3Client) {
        super(s3Client);
        this.s3Client = s3Client;
    }

    @Override
    public CreateBucketResponse addBucket(@RequestBody CreateBucketRequest bucketRequest) {
        log.info("S3ServiceImpl :: addBucket :: bucketRequest :: {}", bucketRequest);
        String s3BucketLocation = createS3Bucket(bucketRequest.getBucketName());
        return CreateBucketResponse
                .builder()
                .bucketName(bucketRequest.getBucketName())
                .location(s3BucketLocation)
                .status(BucketStatus.CREATED)
                .build();
    }

    @Override
    public List<String> getBuckets() {
        return getBucketList();
    }

    @Override
    public DeleteBucketResponse deleteBucket(@RequestBody DeleteBucketRequest deleteBucketRequest) {
        log.info("S3ServiceImpl :: deleteBucket :: bucketRequest :: {}", deleteBucketRequest);
        return deleteBucket(deleteBucketRequest.getBucketName()) ?
                getDeleteBucketResponse(deleteBucketRequest.getBucketName(), BucketStatus.DELETED) :
                getDeleteBucketResponse(deleteBucketRequest.getBucketName(), BucketStatus.NOT_DELETED);


    }

    private DeleteBucketResponse getDeleteBucketResponse(String bucketName, BucketStatus bucketStatus) {
        return DeleteBucketResponse
                .builder()
                .bucketName(bucketName)
                .status(bucketStatus)
                .build();
    }

    @Override
    public FileResponse addFile(MultipartFile multipartFile, String bucketName) {
        log.info("S3ServiceImpl :: addFile :: bucketName :: {}", bucketName);
        try {
            uploadS3Object(bucketName, multipartFile.getOriginalFilename(), multipartFile.getBytes(), multipartFile.getContentType());
            return getFileResponse(bucketName, multipartFile.getOriginalFilename(), FileStatus.UPLOADED);

        } catch (IOException ioException) {
            log.error(ioException.getCause().getMessage());
            return getFileResponse(bucketName, multipartFile.getOriginalFilename(), FileStatus.NOT_UPLOADED);
        }

    }

    @Override
    public List<String> viewFiles(FileRequest fileRequest) {
        return getFiles(fileRequest.bucketName);
    }

    @Override
    public FileResponse deleteFile(DeleteFileRequest fileRequest) {
        log.info("S3ServiceImpl :: addFile :: fileRequest :: bucketName:: {} :: fileName :: {}", fileRequest.getBucketName(), fileRequest.getFileName());
        deleteObjectFromS3(fileRequest.getBucketName(), fileRequest.getFileName());
        return getFileResponse(fileRequest.getBucketName(), fileRequest.getFileName(), FileStatus.DELETED);
    }

    private static FileResponse getFileResponse(String fileRequest, String fileRequest1, FileStatus deleted) {
        return FileResponse.builder()
                .bucketName(fileRequest)
                .fileName(fileRequest1)
                .status(deleted)
                .build();
    }
}
