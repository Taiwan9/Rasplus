package com.client_ws.rasmooplus.repository;

import com.client_ws.rasmooplus.model.UserPaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaymentInfoRepository extends JpaRepository<UserPaymentInfo, Long> {
}
