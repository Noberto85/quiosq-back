

INSERT INTO contador_pedido_quiosque (quiosque_id, valor)
SELECT id, 1 FROM quiosque;

CREATE OR REPLACE FUNCTION gerar_codigo_pedido()
RETURNS TRIGGER AS $$
DECLARE
    v_contador INT;
BEGIN
    -- Busca o contador do quiosque
    SELECT valor INTO v_contador
    FROM contador_pedido_quiosque
    WHERE quiosque_id = NEW.quiosque_id;

    -- Gera o código com 5 dígitos
    NEW.codigo := LPAD(v_contador::text, 5, '0');

    -- Atualiza o contador
    IF v_contador >= 99999 THEN
        UPDATE contador_pedido_quiosque
        SET valor = 1
        WHERE quiosque_id = NEW.quiosque_id;
    ELSE
        UPDATE contador_pedido_quiosque
        SET valor = v_contador + 1
        WHERE quiosque_id = NEW.quiosque_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_gerar_codigo_pedido
BEFORE INSERT ON pedido
FOR EACH ROW
EXECUTE FUNCTION gerar_codigo_pedido();