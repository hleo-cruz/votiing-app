CREATE TABLE voto (
    id UUID PRIMARY KEY,
    id_associado UUID NOT NULL,
    id_sessao UUID NOT NULL,
    escolha VARCHAR(5) NOT NULL,
    CONSTRAINT fk_voto_associado FOREIGN KEY (id_associado) REFERENCES associado(id),
    CONSTRAINT fk_voto_sessao FOREIGN KEY (id_sessao) REFERENCES sessao(id)
);
