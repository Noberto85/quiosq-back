ALTER TABLE pedido
ADD COLUMN cliente_id BIGINT NOT NULL,
ADD CONSTRAINT fk_pedido_cliente
    FOREIGN KEY (cliente_id) REFERENCES cliente(id);