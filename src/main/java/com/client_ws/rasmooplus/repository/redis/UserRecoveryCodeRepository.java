package com.client_ws.rasmooplus.repository.redis;

import com.client_ws.rasmooplus.model.redis.UserRecoveryCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRecoveryCodeRepository extends CrudRepository<UserRecoveryCode, String> {
    Optional<UserRecoveryCode> findByEmail(String email);
}
