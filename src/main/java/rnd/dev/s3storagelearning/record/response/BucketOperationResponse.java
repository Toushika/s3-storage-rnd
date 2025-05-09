package rnd.dev.s3storagelearning.record.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import rnd.dev.s3storagelearning.constant.enums.BucketStatus;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BucketOperationResponse {
    private String bucketName;
    private BucketStatus status;
    private String location;
}
