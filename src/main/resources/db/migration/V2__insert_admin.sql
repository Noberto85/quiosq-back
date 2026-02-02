-- Inserindo os valores padrão
INSERT INTO tb_roles (nome) VALUES ('ROLE_SYSTEM_ADMIN'),('ROLE_ADMIN'),('ROLE_GARCOM'),('ROLE_CLIENTE'),('ROLE_COZINHA');
INSERT INTO tb_users (email,nome, password, active) values('sys@admin','ROLE_SYSTEM_ADMIN','$2a$10$DwkRw2YuIMDEkCHxfXYz5uZb7mnYMghWAaJLpGRZC96or5HjD0QaO', true);

INSERT INTO tb_users_roles (role_id, user_id)
SELECT 1, id
FROM tb_users
WHERE email = 'sys@admin';



