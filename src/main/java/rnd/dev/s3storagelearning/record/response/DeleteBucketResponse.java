package rnd.dev.s3storagelearning.record.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@ToString(callSuper = true)
public class DeleteBucketResponse extends BucketOperationResponse {

}
