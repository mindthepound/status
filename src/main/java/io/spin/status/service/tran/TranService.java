package io.spin.status.service.tran;

import io.spin.status.dto.Result;
import io.spin.status.dto.emma.TranDTO;

public interface TranService {
    Result saveTranMessage(TranDTO tranDTO) throws Exception;
}