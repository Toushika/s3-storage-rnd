package rnd.dev.s3storagelearning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class S3Controller {
    @PostMapping("/create/bucket")
    public String createBucket() {
        return "";
    }

    @PostMapping("/delete/bucket")
    public String deleteBucket() {
        return "";
    }


}
