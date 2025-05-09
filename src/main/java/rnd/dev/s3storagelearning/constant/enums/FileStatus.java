package rnd.dev.s3storagelearning.constant.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FileStatus {
    UPLOADED("UPLOADED"),
    NOT_UPLOADED("NOT_UPLOADED"),
    DELETED("DELETED"),
    NOT_DELETED("NOT_DELETED");

    @Getter(onMethod_ = {@JsonValue})
    private final String value;
}
