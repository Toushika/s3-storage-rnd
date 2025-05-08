package rnd.dev.s3storagelearning.record.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteFileRequest {
    private String bucketName;
    private String fileName;
}
