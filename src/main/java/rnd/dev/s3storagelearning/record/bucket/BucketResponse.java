package rnd.dev.s3storagelearning.record.bucket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BucketResponse {
    private String bucketName;
    private Status status;
    private String location;

    public enum Status {
        CREATED,
        DELETED
    }
}
