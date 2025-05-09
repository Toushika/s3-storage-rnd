package rnd.dev.s3storagelearning.record.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rnd.dev.s3storagelearning.constant.enums.FileStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private String fileName;
    private String bucketName;
    private FileStatus status;
}
