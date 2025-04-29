package com.br.voting.repository;

import com.br.voting.enums.StatusPauta;
import com.br.voting.model.Pauta;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PautaRepository {

    private final JdbcTemplate jdbcTemplate;

    public PautaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void salvar(Pauta pauta) {
        String sql = "INSERT INTO pauta (id, titulo, descricao, status) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, pauta.getId(), pauta.getTitulo(), pauta.getDescricao(), pauta.getStatus().name());
    }

    public List<Pauta> listarTodas() {
        return jdbcTemplate.query("SELECT * FROM pauta", pautaMapper);
    }

    public Optional<Pauta> buscarPorId(UUID id) {
        List<Pauta> result = jdbcTemplate.query("SELECT * FROM pauta WHERE id = ?", pautaMapper, id);
        return result.stream().findFirst();
    }

    public void atualizarStatus(UUID id, String statusPauta) {
        String sql = "UPDATE pauta SET status_pauta = ? WHERE id = ?";
        jdbcTemplate.update(sql, statusPauta, id);
    }

    private RowMapper<Pauta> pautaMapper = (ResultSet rs, int rowNum) -> {
        Pauta pauta = new Pauta();
        pauta.setId(UUID.fromString(rs.getString("id")));
        pauta.setTitulo(rs.getString("titulo"));
        pauta.setDescricao(rs.getString("descricao"));
        pauta.setStatus(StatusPauta.valueOf(rs.getString("status")));
        return pauta;
    };
}
