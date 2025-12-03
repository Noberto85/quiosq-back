CREATE OR REPLACE FUNCTION get_mesa_info(p_quiosque_id UUID, p_mesa_id BIGINT)
RETURNS TABLE (
    quiosque_id UUID,
    garcom_nome VARCHAR,
    quiosque_nome VARCHAR,
    mesa_numero INT
)
AS $$
BEGIN
    RETURN QUERY
    SELECT 
		q.id AS quiosque_id,
        g.nome AS garcom_nome,
        q.nome AS quiosque_nome,
        m.numero AS mesa_numero
    FROM mesa m
    JOIN garcom g ON m.garcom_id = g.id
    JOIN quiosque q ON m.quiosque_id = q.id
    WHERE m.quiosque_id = p_quiosque_id
      AND m.id = p_mesa_id;
END;
$$ LANGUAGE plpgsql;