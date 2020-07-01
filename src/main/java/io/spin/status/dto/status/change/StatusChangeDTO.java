package io.spin.status.dto.status.change;

import io.spin.status.domain.dynamoDB.status.server.StatusServerStatusChange;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StatusChangeDTO extends StatusServerStatusChange implements Serializable {

    private static final long serialVersionUID = 6898086624593122830L;

    private String id;
}
