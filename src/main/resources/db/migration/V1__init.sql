-- Extensão para gerar UUIDs automaticamente
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Tabela de quiosque
CREATE TABLE quiosque (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cnpj VARCHAR(20) UNIQUE NOT NULL
);

-- Tabela de roles
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) UNIQUE NOT NULL
);

-- Tabela de usuários
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    email VARCHAR(100) UNIQUE NOT NULL,
    nome VARCHAR(100),
    image BYTEA,
    password VARCHAR(200) NOT NULL,
    quiosque_id UUID REFERENCES quiosque(id) ON DELETE SET NULL
);

-- Tabela de relacionamento ManyToMany entre users e roles
CREATE TABLE users_roles (
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    role_id INT REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

-- Tabela Garçom
CREATE TABLE garcom (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    quiosque_id UUID,
    CONSTRAINT fk_garcom_quiosque FOREIGN KEY (quiosque_id) REFERENCES quiosque(id)
);

-- Tabela Mesa
CREATE TABLE mesa (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    numero INT NOT NULL,
    status VARCHAR(15) NOT NULL,
    garcom_id BIGINT,
    quiosque_id UUID,
	  UNIQUE(numero,quiosque_id),
    CONSTRAINT fk_mesa_quiosque FOREIGN KEY (quiosque_id) REFERENCES quiosque(id),
    CONSTRAINT fk_mesa_garcom FOREIGN KEY (garcom_id) REFERENCES garcom(id)
);

-- Tabela Pedido
CREATE TABLE pedido (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    status VARCHAR(30) NOT NULL,
    codigo VARCHAR(5) NOT NULL,
    mesa_id BIGINT,
    quiosque_id UUID,
    data_init TIMESTAMP  NOT NULL,
    data_fim TIMESTAMP,
    CONSTRAINT fk_pedido_mesa FOREIGN KEY (mesa_id) REFERENCES mesa(id),
    CONSTRAINT fk_pedido_quiosque FOREIGN KEY (quiosque_id) REFERENCES quiosque(id)
);

CREATE TABLE categoria (
    id BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE item_cardapio (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    imagem BYTEA,
    url_imagem VARCHAR(255),
    avaliacao INT,
    categoria_id BIGINT NOT NULL,
    quiosque_id UUID,
    CONSTRAINT fk_cardapio_quiosque FOREIGN KEY (quiosque_id) REFERENCES quiosque(id),
    CONSTRAINT fk_cardapio_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);


-- Tabela ItemPedido
CREATE TABLE item_pedido (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    quantidade INT NOT NULL,
    valor_soma DECIMAL(10,2),
    pedido_id BIGINT,
    item_cardapio_id BIGINT,
    CONSTRAINT fk_itempedido_pedido FOREIGN KEY (pedido_id) REFERENCES pedido(id),
    CONSTRAINT fk_itempedido_cardapio FOREIGN KEY (item_cardapio_id) REFERENCES item_cardapio(id)
);


-- Índices úteis
CREATE INDEX idx_users_quiosque ON users(quiosque_id);
CREATE INDEX idx_users_roles_user ON users_roles(user_id);
CREATE INDEX idx_users_roles_role ON users_roles(role_id);