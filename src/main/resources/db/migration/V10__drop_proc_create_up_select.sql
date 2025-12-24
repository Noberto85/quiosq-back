DROP PROCEDURE IF EXISTS create_pedido;

CREATE OR REPLACE FUNCTION create_pedido(
    quiosque_in UUID,
    mesa_in BIGINT,
    telefone_in VARCHAR,
    itens_in item_pedido_type[]
)
RETURNS TABLE(pedido_id BIGINT, codigo_pedido VARCHAR)
AS $$
DECLARE
    var_cont  contador_pedido_quiosque.quiosque_id%type;
    varclienteId cliente.id%type;
    quiosqueId quiosque.id%type;
    mesaId mesa.id%type;
    precoUnit item_cardapio.preco%type;
    item item_pedido_type;
BEGIN
    -- Verifica cliente pelo telefone
    SELECT id INTO varclienteId
    FROM cliente
    WHERE telefone = telefone_in;

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
    VALUES ('AGUARDANDO PAGAMENTO', mesaId, quiosqueId, NOW(), varclienteId)
    RETURNING id, codigo INTO pedido_id, codigo_pedido;

    -- Insere itens
    FOREACH item IN ARRAY itens_in
    LOOP
        SELECT (preco * item.quantidade) INTO precoUnit
        FROM item_cardapio
        WHERE id = item.item_id;

        INSERT INTO item_pedido (quantidade, valor_soma, pedido_id, item_cardapio_id)
        VALUES (item.quantidade, precoUnit, pedido_id, item.item_id);
    END LOOP;

    RETURN NEXT;
END;
$$ LANGUAGE plpgsql;