package rnd.dev.s3storagelearning.constant.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BucketStatus {
    CREATED("CREATED"),
    DELETED("DELETED"),
    NOT_DELETED("NOT_DELETED");

    @Getter(onMethod_ = {@JsonValue})
    private final String value;
}


