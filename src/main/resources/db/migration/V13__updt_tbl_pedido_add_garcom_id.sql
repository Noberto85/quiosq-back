
ALTER TABLE tb_pedido
ADD COLUMN garcom_id BIGINT NULL,
ADD CONSTRAINT fk_pedido_garcom
    FOREIGN KEY (garcom_id) REFERENCES tb_garcom(id);

-- ADICIONANDO NA FUNCION

DROP FUNCTION func_create_pedido;

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
    var_cont  tb_contador_pedido_quiosque.quiosque_id%type;
    varclienteId tb_cliente.id%type;
    quiosqueId tb_quiosque.id%type;
    mesaId tb_mesa.id%type;
    garcomId tb_garcom.id%type;
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
    SELECT
      me.id,ga.id
      INTO
      mesaId,garcomId
    FROM tb_mesa me
    INNER join tb_garcom ga ON ga.id = me.garcom_id
    WHERE me.numero = mesa_in AND me.quiosque_id = quiosque_in;

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
    INSERT INTO tb_pedido (nome_pedido, status, mesa_id, quiosque_id, garcom_id, data_init, cliente_id)
    VALUES (nome_pedido_in,'AGUARDANDO_PAGAMENTO', mesaId, quiosqueId, garcomId, NOW(), varclienteId)
    RETURNING id, codigo INTO pedido_id, codigo_pedido;

    -- Insere itens
    FOREACH item IN ARRAY itens_in
    LOOP
        SELECT (preco * item.quantidade) INTO precoUnit
        FROM tb_item_cardapio
        WHERE id = item.item_id;

        INSERT INTO tb_item_pedido (quantidade, valor_soma, pedido_id, item_cardapio_id)
        VALUES (item.quantidade, precoUnit, pedido_id, item.item_id);
    END LOOP;

    RETURN NEXT;
END;
$$ LANGUAGE plpgsql;
