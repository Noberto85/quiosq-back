package com.webone.quiosq.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_mercadopago_tokens")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MercadoPagoToken {

    @Id
    @Column
    private Long userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String accessToken;

    @Column(nullable = false, length = 20)
    private String tokenType;

    @Column(nullable = false)
    private Long expiresIn;

    @Column
    private String scope;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String refreshToken;

    @Column
    private String publicKey;

    @Column(nullable = false)
    private Boolean liveMode;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "quiosque_id", nullable = false)
    private Quiosque quiosque;

}
