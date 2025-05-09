package rnd.dev.s3storagelearning.record.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rnd.dev.s3storagelearning.constant.enums.BucketStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBucketResponse {
    private String bucketName;
    private BucketStatus status;
    private String location;

}
