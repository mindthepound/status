package io.spin.status.enumeration;

public enum Buckets {

    IMAGES("spin-images"),
    DynamoDB("spin-dynamodb"),
    SETTINGS("spin-settings")
    ;

    final private String bucketName;

    Buckets(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
