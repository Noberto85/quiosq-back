package com.webone.quiosq.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.webone.quiosq.entity.enums.StatusMesaEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_mesa")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer numero;

    private Boolean ativo;

    @ManyToOne
    @JoinColumn(name = "garcom_id")
    @JsonBackReference
    private Garcom garcom;

    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Pedido> pedidos;

    @Enumerated(EnumType.STRING)
    private StatusMesaEnum status;

    @ManyToOne
    @JoinColumn(name = "quiosque_id")
    private Quiosque quiosque;

    @PrePersist
    public void prePersist() {
        this.ativo = Boolean.TRUE;
    }
}

