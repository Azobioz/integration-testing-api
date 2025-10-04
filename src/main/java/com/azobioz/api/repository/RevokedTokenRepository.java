package com.azobioz.api.repository;

import com.azobioz.api.module.RevokeToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevokedTokenRepository extends JpaRepository<RevokeToken, Long> {
    boolean existsByToken(String token);
}
