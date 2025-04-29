package com.br.voting.repository;

import com.br.voting.enums.EscolhaVoto;
import com.br.voting.model.Voto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class VotoRepository {

    private final JdbcTemplate jdbcTemplate;

    public VotoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void salvar(Voto voto) {
        String sql = "INSERT INTO voto (id, id_associado, id_sessao, escolha) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, voto.getId(), voto.getIdAssociado(), voto.getIdSessao(), voto.getEscolha().name());
    }

    public List<Voto> listarTodos() {
        return jdbcTemplate.query("SELECT * FROM voto", votoMapper);
    }

    public boolean existsByIdAssociadoAndIdSessao(UUID idAssociado, UUID idSessao) {
        String sql = "SELECT COUNT(1) FROM voto WHERE id_associado = ? AND id_sessao = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idAssociado, idSessao);
        return count != null && count > 0;
    }

    public Optional<Voto> buscarPorId(UUID id) {
        List<Voto> result = jdbcTemplate.query("SELECT * FROM voto WHERE id = ?", votoMapper, id);
        return result.stream().findFirst();
    }

    public List<Voto> buscarPorIdSessao(UUID idSessao) {
        String sql = "SELECT * FROM voto WHERE id_sessao = ?";
        return jdbcTemplate.query(sql, votoMapper, idSessao);
    }

    private RowMapper<Voto> votoMapper = (ResultSet rs, int rowNum) -> {
        Voto voto = new Voto();
        voto.setId(UUID.fromString(rs.getString("id")));
        voto.setIdAssociado(UUID.fromString(rs.getString("id_associado")));
        voto.setIdSessao(UUID.fromString(rs.getString("id_sessao")));
        voto.setEscolha(EscolhaVoto.valueOf(rs.getString("escolha"))
);
        return voto;
    };
}
