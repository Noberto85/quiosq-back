package com.webone.quiosq.repository;

import com.webone.quiosq.entity.MercadoPagoToken;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MercadoPagoTokenRepository extends JpaRepository<MercadoPagoToken, Long> {
    Optional<MercadoPagoToken> findByUserId(Long userId);

    @Query("SELECT m.accessToken FROM MercadoPagoToken m JOIN m.quiosque q WHERE q.id = :quiosqueId")
    Optional<String> findAccessTokenByQuiosqueId(@Param("quiosqueId") UUID quiosqueId);
}
