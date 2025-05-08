package rnd.dev.s3storagelearning.record.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    public String fileName;
    public String bucketName;
    private Status status;

    public enum Status {
        CREATED, DELETED
    }
}
