package io.spin.status.enumeration;

public enum ServiceSubType {
    EVENT("event"),
    ;
    final private String type;

    ServiceSubType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
