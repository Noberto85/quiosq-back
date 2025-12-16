package com.webone.quiosq.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cliente_quiosque")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteQuiosque {

    @EmbeddedId
    private ClienteQuiosqueId id;

    @ManyToOne
    @MapsId("clienteId")
    private Cliente cliente;

    @ManyToOne
    @MapsId("quiosqueId")
    private Quiosque quiosque;

    @Column(nullable = false)
    private LocalDateTime dataUltimoAcesso;

}
