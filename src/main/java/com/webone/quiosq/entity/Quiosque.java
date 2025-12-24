package com.webone.quiosq.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "quiosque")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quiosque {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    private String telefone;

    private String email;

    @Column(unique = true, nullable = false)
    private String cnpj;

    @ManyToMany(mappedBy = "quiosques")
    private Set<Cliente> clientes;


}
