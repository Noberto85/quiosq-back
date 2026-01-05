CREATE TABLE tb_mercadopago_tokens (
    id SERIAL PRIMARY KEY,
    quiosque_id UUID NOT NULL,
    access_token TEXT NOT NULL,
    token_type VARCHAR(20) NOT NULL,
    expires_in BIGINT NOT NULL,
    scope TEXT,
    user_id BIGINT NOT NULL,
    refresh_token TEXT NOT NULL,
    public_key TEXT,
    live_mode BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_quiosque FOREIGN KEY (quiosque_id) REFERENCES tb_quiosque(id) ON DELETE CASCADE
);

CREATE TABLE tb_endereco (
    id SERIAL PRIMARY KEY,
    logradouro VARCHAR(150),
    numero VARCHAR(20),
    complemento VARCHAR(100),
    bairro VARCHAR(100),
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    cep VARCHAR(15) NOT NULL,
    pais VARCHAR(50) DEFAULT 'Brasil',
    quiosque_id UUID UNIQUE,
    CONSTRAINT fk_quiosque FOREIGN KEY (quiosque_id) REFERENCES tb_quiosque(id) ON DELETE CASCADE
);

ALTER TABLE  tb_quiosque ADD COLUMN telefone VARCHAR(20);
ALTER TABLE  tb_quiosque ADD COLUMN email VARCHAR(255);