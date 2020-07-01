package io.spin.status.exception;

import io.spin.status.enumeration.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResultException extends RuntimeException {

    private ResultCode resultCode;

    public ResultException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

}
