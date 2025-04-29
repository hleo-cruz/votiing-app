CREATE TABLE sessao (
    id UUID PRIMARY KEY,
    id_pauta UUID NOT NULL,
    data_hora_inicio TIMESTAMP NOT NULL,
    data_hora_fim TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    CONSTRAINT fk_sessao_pauta FOREIGN KEY (id_pauta) REFERENCES pauta(id)
);
