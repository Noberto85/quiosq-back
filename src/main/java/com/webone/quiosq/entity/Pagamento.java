package com.webone.quiosq.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_pagamento")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long mpPagId;

    // Relacionamento com Pedido
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(nullable = false, length = 30)
    private String status; // ex: pending, approved, rejected

    @Column(name = "status_detail", length = 50)
    private String statusDetail; // ex: pending_waiting_transfer

    @Column(nullable = false, length = 20)
    private String metodo; // ex: pix, credit_card

    @Column(length = 30)
    private String tipo; // ex: bank_transfer

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false, length = 5)
    private String moeda; // ex: BRL

    @Column(name = "external_reference", length = 100)
    private String externalReference;

    @Column(name = "transaction_id", length = 100)
    private String transactionId;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_expiracao")
    private LocalDateTime dataExpiracao;

    @Column(name = "data_aprovacao")
    private LocalDateTime dataAprovacao;

}
