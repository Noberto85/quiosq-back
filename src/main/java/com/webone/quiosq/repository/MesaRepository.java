package com.webone.quiosq.repository;

import com.webone.quiosq.entity.Garcom;
import com.webone.quiosq.entity.Mesa;
import com.webone.quiosq.projection.MesaInfoProjection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MesaRepository extends JpaRepository<Mesa, Long>,
    JpaSpecificationExecutor<Mesa> {

    @Query(value = "SELECT * FROM func_get_mesa_info(:quiosqueId, :mesaId)", nativeQuery = true)
    Optional<MesaInfoProjection> getMesaInfo(@Param("quiosqueId") UUID quiosqueId,
        @Param("mesaId") Long mesaId);

    @Query("SELECT m.id FROM Mesa m INNER JOIN m.quiosque qui WHERE  m.numero =:numero and qui.id =:quiosqueId ")
    Optional<Long> getID(@Param("quiosqueId") UUID quiosqueId, @Param("numero") Integer numero);

    List<Mesa> findByGarcom(Garcom garcom);

    @Query("SELECT m FROM Mesa m JOIN m.quiosque qui WHERE  m.numero =:numero and qui.id =:quiosqueId ")
    Optional<Mesa> findByNumeroAndQuiosque(@Param("quiosqueId") UUID quiosqueId,
        @Param("numero") Integer numero);


}
