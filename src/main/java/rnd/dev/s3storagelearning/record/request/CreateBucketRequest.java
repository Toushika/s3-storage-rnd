package rnd.dev.s3storagelearning.record.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@ToString

public class CreateBucketRequest extends BucketOperationRequest {

}
