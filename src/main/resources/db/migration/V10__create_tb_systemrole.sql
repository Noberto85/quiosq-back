CREATE TABLE System_role (
    id BIGSERIAL PRIMARY KEY,
    taxa DECIMAL(10,2) NOT NULL,
    data_taxa TIMESTAMP NOT NULL
);
INSERT INTO System_role (taxa, data_taxa)
VALUES (0.90, NOW());