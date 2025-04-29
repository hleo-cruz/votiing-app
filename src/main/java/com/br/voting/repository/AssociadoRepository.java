package com.br.voting.repository;

import com.br.voting.enums.StatusAssociado;
import com.br.voting.model.Associado;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AssociadoRepository {

    private final JdbcTemplate jdbcTemplate;

    public AssociadoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void salvar(Associado associado) {
        String sql = "INSERT INTO associado (id, nome, cpf, status) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, associado.getId(), associado.getNome(), associado.getCpf(), associado.getStatus().name());
    }

    public List<Associado> listarTodos() {
        return jdbcTemplate.query("SELECT * FROM associado", associadoMapper);
    }

    public Optional<Associado> buscarPorId(UUID id) {
        List<Associado> result = jdbcTemplate.query("SELECT * FROM associado WHERE id = ?", associadoMapper, id);
        return result.stream().findFirst();
    }

    public void desativar(UUID id) {
        jdbcTemplate.update("UPDATE associado SET status = 'INATIVO' WHERE id = ?", id);
    }

    private RowMapper<Associado> associadoMapper = (ResultSet rs, int rowNum) -> {
        Associado associado = new Associado();
        associado.setId(UUID.fromString(rs.getString("id")));
        associado.setNome(rs.getString("nome"));
        associado.setCpf(rs.getString("cpf"));
        associado.setStatus(StatusAssociado.valueOf(rs.getString("status")));
        return associado;
    };
}
