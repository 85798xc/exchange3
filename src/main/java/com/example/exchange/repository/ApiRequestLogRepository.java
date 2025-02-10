package com.example.exchange.repository;

import com.example.exchange.entity.ApiRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRequestLogRepository extends JpaRepository<ApiRequestLog, Long> {

}
