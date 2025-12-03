package com.webone.quiosq.repository;

import com.webone.quiosq.entity.Mesa;
import com.webone.quiosq.projection.MesaInfoProjection;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MesaRepository extends JpaRepository<Mesa, Long> {

    @Query(value = "SELECT * FROM get_mesa_info(:quiosqueId, :mesaId)", nativeQuery = true)
    Optional<MesaInfoProjection> getMesaInfo(@Param("quiosqueId") UUID quiosqueId,
        @Param("mesaId") Long mesaId);

}
