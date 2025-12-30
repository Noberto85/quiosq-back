CREATE TABLE pagamento (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    pedido_id BIGINT NOT NULL,
    mp_pag_id BIGINT NOT NULL,
    status VARCHAR(30) NOT NULL, -- ex: pending, approved, rejected
    status_detail VARCHAR(50),   -- ex: pending_waiting_transfer
    metodo VARCHAR(20) NOT NULL, -- ex: pix, credit_card
    tipo VARCHAR(30),            -- ex: bank_transfer
    valor NUMERIC(10,2) NOT NULL,
    moeda VARCHAR(5) NOT NULL,   -- ex: BRL
    external_reference VARCHAR(100), -- #pedido Id 123
    transaction_id VARCHAR(100),     -- id da transação
    data_criacao TIMESTAMP NOT NULL,
    data_expiracao TIMESTAMP,
    data_aprovacao TIMESTAMP,
    CONSTRAINT fk_pagamento_pedido FOREIGN KEY (pedido_id) REFERENCES pedido(id)
);