-- Extensão para gerar UUIDs automaticamente
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Tabela de quiosque
CREATE TABLE tb_quiosque (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cnpj VARCHAR(20) UNIQUE NOT NULL,
    ativo BOOLEAN NOT NULL
);

-- Tabela de roles
CREATE TABLE tb_roles (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) UNIQUE NOT NULL
);

-- Tabela de usuários
CREATE TABLE tb_users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    email VARCHAR(100) UNIQUE,
    nome VARCHAR(100),
    telefone VARCHAR(11),
    cpf VARCHAR(11),
    image BYTEA,
    password VARCHAR(200) NOT NULL,
    quiosque_id UUID REFERENCES tb_quiosque(id) ON DELETE SET NULL,
    active BOOLEAN NOT NULL,
    UNIQUE(cpf,quiosque_id,telefone)
);

-- Tabela de relacionamento ManyToMany entre users e roles
CREATE TABLE tb_users_roles (
    user_id UUID REFERENCES tb_users(id) ON DELETE CASCADE,
    role_id INT REFERENCES tb_roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

-- Tabela Mesa
CREATE TABLE tb_mesa (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    numero INT NOT NULL,
    status VARCHAR(15) NOT NULL,
    garcom_id UUID,
    quiosque_id UUID,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
	  UNIQUE(numero,quiosque_id),
    CONSTRAINT fk_mesa_quiosque FOREIGN KEY (quiosque_id) REFERENCES tb_quiosque(id),
    CONSTRAINT fk_mesa_garcom FOREIGN KEY (garcom_id) REFERENCES tb_users(id)
);

-- Tabela Pedido
CREATE TABLE tb_pedido (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    status VARCHAR(30) NOT NULL,
    codigo VARCHAR(5) NOT NULL,
    mesa_id BIGINT,
    quiosque_id UUID,
    data_init TIMESTAMP  NOT NULL,
    data_fim TIMESTAMP,
    data_contagem TIMESTAMP,
    CONSTRAINT fk_pedido_mesa FOREIGN KEY (mesa_id) REFERENCES tb_mesa(id),
    CONSTRAINT fk_pedido_quiosque FOREIGN KEY (quiosque_id) REFERENCES tb_quiosque(id)
);

CREATE TABLE tb_categoria (
    id BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    quiosque_id UUID NOT NULL,
    CONSTRAINT fk_categoria_quiosque FOREIGN KEY (quiosque_id) REFERENCES tb_quiosque(id)
);

CREATE TABLE tb_item_cardapio (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    imagem BYTEA,
    url_imagem VARCHAR(255),
    avaliacao INT,
    categoria_id BIGINT NOT NULL,
    quiosque_id UUID,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_cardapio_quiosque FOREIGN KEY (quiosque_id) REFERENCES tb_quiosque(id),
    CONSTRAINT fk_cardapio_categoria FOREIGN KEY (categoria_id) REFERENCES tb_categoria(id)
);


-- Tabela ItemPedido
CREATE TABLE tb_item_pedido (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    quantidade INT NOT NULL,
    valor_soma DECIMAL(10,2),
    pedido_id BIGINT,
    item_cardapio_id BIGINT,
    CONSTRAINT fk_itempedido_pedido FOREIGN KEY (pedido_id) REFERENCES tb_pedido(id),
    CONSTRAINT fk_itempedido_cardapio FOREIGN KEY (item_cardapio_id) REFERENCES tb_item_cardapio(id)
);


-- Índices úteis
CREATE INDEX idx_users_quiosque ON tb_users(quiosque_id);
CREATE INDEX idx_users_roles_user ON tb_users_roles(user_id);
CREATE INDEX idx_users_roles_role ON tb_users_roles(role_id);