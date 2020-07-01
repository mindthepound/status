package io.spin.status.sync.db.mapper;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Component
public @interface SyncConnMapper {
}
