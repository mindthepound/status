package io.spin.status.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.spin.status.enumeration.ResultCode;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonSerialize
public class Result implements Serializable {

    private static final long serialVersionUID = 8424154614196119451L;

    private String code;
    private String msg;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Object data;

    public Result(ResultCode resultCode) {
        code = resultCode.getCode();
        msg = resultCode.getMsg();
    }

}
