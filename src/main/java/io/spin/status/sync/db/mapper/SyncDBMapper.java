package io.spin.status.sync.db.mapper;

import io.spin.status.dto.emma.TranDTO;

@SyncConnMapper
public interface SyncDBMapper {
    // em_smt_tran Insert
    public void insertTran(TranDTO tranDTO) throws Exception;
}
