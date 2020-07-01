package io.spin.status.util.redis;

public interface MessagePublisher {

    void publish(String message);

}
