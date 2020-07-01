package io.spin.status.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transfer implements Serializable {

    private static final long serialVersionUID = 6009183223175757901L;

    private String from;
    private String to;

}
