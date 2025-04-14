package com.client_ws.rasmooplus.repository.redis;

import com.client_ws.rasmooplus.model.redis.RecoveryCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecoveryCodeRepository extends CrudRepository<RecoveryCode, String> {
}
