DROP PROCEDURE create_pedido;

CREATE OR REPLACE PROCEDURE create_pedido(
    IN quiosque_in UUID,
    IN mesa_in BIGINT,
    IN telefone_in VARCHAR,
    IN itens_in item_pedido_type[]
)
AS $$
DECLARE
	var_cont  contador_pedido_quiosque.quiosque_id%type;
	varclienteId cliente.id%type;
    quiosqueId quiosque.id%type;
    mesaId mesa.id%type;
    pedidoId pedido.id%type;
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
    WHERE numero = mesa_in AND quiosque_id = quiosque_in;

    IF quiosqueId IS NULL OR mesaId IS NULL THEN
        RAISE EXCEPTION 'MESA OU QUIOSQUE NÃO ENCONTRADO';
    END IF;

    SELECT quiosque_id INTO var_cont
    FROM contador_pedido_quiosque
    WHERE quiosque_id = quiosque_in;
    -- INICIA A CONTAGEM DO PEDIDO PARA CADA QUIOSQUE
    IF var_cont IS NULL THEN
      INSERT INTO contador_pedido_quiosque (quiosque_id, valor)
      VALUES (quiosqueId, 1);
      END IF;

    -- Cria pedido
    INSERT INTO pedido (status, mesa_id, quiosque_id, data_init, cliente_id)
    VALUES ('AGUARDANDO PAGAMENTO', mesaId, quiosqueId, NOW(), varclienteId)
    RETURNING id INTO pedidoId;

    -- Insere itens
    FOREACH item IN ARRAY itens_in
    LOOP
        SELECT (preco * item.quantidade) INTO precoUnit
        FROM item_cardapio
        WHERE id = item.item_id;

        INSERT INTO item_pedido (quantidade, valor_soma, pedido_id, item_cardapio_id)
        VALUES (item.quantidade, precoUnit, pedidoId, item.item_id);
    END LOOP;

END;
$$ LANGUAGE plpgsql;