CREATE OR REPLACE FUNCTION func_create_pedido(
    nome_pedido_in VARCHAR,
    quiosque_in UUID,
    mesa_in BIGINT,
    telefone_in VARCHAR,
    itens_in item_pedido_type[]
)
RETURNS TABLE(pedido_id BIGINT, codigo_pedido VARCHAR)
AS $$
DECLARE
	  var_taxa system_role.taxa%type;
    var_cont  tb_contador_pedido_quiosque.quiosque_id%type;
    varclienteId tb_cliente.id%type;
    quiosqueId tb_quiosque.id%type;
    mesaId tb_mesa.id%type;
    precoUnit tb_item_cardapio.preco%type;
    item item_pedido_type;
BEGIN
    -- Verifica cliente pelo telefone
    SELECT id INTO varclienteId
    FROM tb_cliente
    WHERE telefone = telefone_in;

    -- Verifica quiosque
    SELECT id INTO quiosqueId
    FROM tb_quiosque
    WHERE id = quiosque_in;

    -- Verifica mesa
    SELECT id INTO mesaId
    FROM tb_mesa
    WHERE numero = mesa_in AND quiosque_id = quiosque_in;

    IF quiosqueId IS NULL OR mesaId IS NULL THEN
        RAISE EXCEPTION 'MESA OU QUIOSQUE NÃO ENCONTRADO';
    END IF;

     SELECT quiosque_id INTO var_cont
        FROM tb_contador_pedido_quiosque
        WHERE quiosque_id = quiosque_in;
        -- INICIA A CONTAGEM DO PEDIDO PARA CADA QUIOSQUE
        IF var_cont IS NULL THEN
          INSERT INTO tb_contador_pedido_quiosque (quiosque_id, valor)
          VALUES (quiosqueId, 1);
          END IF;

    -- Cria pedido
    INSERT INTO tb_pedido (nome_pedido,status, mesa_id, quiosque_id, data_init, cliente_id)
    VALUES (nome_pedido_in,'AGUARDANDO_PAGAMENTO', mesaId, quiosqueId, NOW(), varclienteId)
    RETURNING id, codigo INTO pedido_id, codigo_pedido;

	-- recupera a taxa a ser adicionada --
	SELECT taxa  INTO var_taxa
	FROM system_role 
	ORDER BY id DESC LIMIT 1; 

    -- Insere itens--
    FOREACH item IN ARRAY itens_in
    LOOP
        SELECT (preco * item.quantidade + var_taxa) INTO precoUnit
        FROM tb_item_cardapio
        WHERE id = item.item_id;

        INSERT INTO tb_item_pedido (quantidade, valor_soma, pedido_id, item_cardapio_id)
        VALUES (item.quantidade, precoUnit, pedido_id, item.item_id);
    END LOOP;

    RETURN NEXT;
END;
$$ LANGUAGE plpgsql;