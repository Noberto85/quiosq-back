
SET TIMEZONE TO 'America/Sao_Paulo';
CREATE TYPE item_pedido_type AS (
    item_id INTEGER,
    quantidade INTEGER
);

CREATE OR REPLACE PROCEDURE create_pedido(
    IN quiosque_in UUID,
    IN mesa_in BIGINT,
    IN cliente_in BIGINT,
    IN itens_in item_pedido_type[]
)
AS $$
DECLARE
    quiosqueId quiosque.id%type;
    mesaId mesa.id%type;
    pedidoId pedido.id%type;
    precoUnit item_cardapio.preco%type;
    item item_pedido_type;
BEGIN
    -- Verifica quiosque
    SELECT id INTO quiosqueId
    FROM quiosque
    WHERE id = quiosque_in;

    -- Verifica mesa
    SELECT id INTO mesaId
    FROM mesa
    WHERE id = mesa_in AND quiosque_id = quiosque_in;

    IF quiosqueId IS NULL OR mesaId IS NULL THEN
        RAISE EXCEPTION 'MESA OU QUIOSQUE NÃO ENCONTRADO';
    END IF;

    -- Cria pedido
    INSERT INTO pedido (status, mesa_id, quiosque_id, data_init, cliente_id)
    VALUES ('AGUARDANDO PAGAMENTO', mesaId, quiosqueId,NOW(), cliente_in)
    RETURNING id INTO pedidoId;

    -- Insere itens
    FOREACH item IN ARRAY itens_in
    LOOP
        SELECT (preco * item.quantidade) INTO precoUnit
        FROM item_cardapio
        WHERE id = item.item_id;

        INSERT INTO item_pedido (quantidade, preco_unitario, pedido_id, item_cardapio_id)
        VALUES (item.quantidade, precoUnit, pedidoId, item.item_id);
    END LOOP;

END;
$$ LANGUAGE plpgsql;