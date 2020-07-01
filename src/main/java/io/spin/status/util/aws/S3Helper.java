package io.spin.status.util.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import io.spin.status.enumeration.Buckets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Slf4j
@Component
public class S3Helper {

    private AmazonS3 amazonS3;

    @Autowired
    public S3Helper(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void deleteFile(Buckets buckets, String key) {
        amazonS3.deleteObject(buckets.getBucketName(), key);
    }

    public CopyObjectResult copyObject(
            Buckets sourceBucket,
            String source,
            Buckets destinationBucket,
            String destination,
            CannedAccessControlList cannedAccessControlList
    ) {
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(
                sourceBucket.getBucketName(),
                source,
                destinationBucket.getBucketName(),
                destination
        );
        copyObjectRequest.withCannedAccessControlList(cannedAccessControlList);

        return amazonS3.copyObject(copyObjectRequest);
    }

    public PutObjectResult putObject(
            byte[] bytes,
            Buckets buckets,
            String key,
            String fileContentType,
            CannedAccessControlList cannedAccessControlList
    ) {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(fileContentType);

        PutObjectRequest putObjectRequest = new PutObjectRequest(
                buckets.getBucketName(),
                key,
                byteArrayToFileInputStream(bytes),
                objectMetadata
        );

        putObjectRequest.withCannedAcl(cannedAccessControlList);

        return amazonS3.putObject(putObjectRequest);
    }

    public PutObjectResult putObject(
            MultipartFile file,
            Buckets buckets,
            String key,
            String fileContentType,
            CannedAccessControlList cannedAccessControlList
    ) {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(fileContentType);

        PutObjectRequest putObjectRequest = new PutObjectRequest(
                buckets.getBucketName(),
                key,
                multipartFileToFileInputStream(file),
                objectMetadata
        );

        putObjectRequest.withCannedAcl(cannedAccessControlList);

        return amazonS3.putObject(putObjectRequest);
    }

    private InputStream byteArrayToFileInputStream(
            byte[] bytes
    ) {
        return new ByteArrayInputStream(bytes);
    }

    private FileInputStream multipartFileToFileInputStream(
            MultipartFile multipartFile
    ) {

        File file = new File(System.getProperty("java.io.tmpdir")+"/"+multipartFile.getOriginalFilename());

        try {

            multipartFile.transferTo(file);
            return new FileInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
