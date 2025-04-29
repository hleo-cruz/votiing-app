package com.br.voting.repository;

import com.br.voting.enums.StatusSessao;
import com.br.voting.model.Sessao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SessaoRepository {

    private final JdbcTemplate jdbcTemplate;

    public SessaoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void salvar(Sessao sessao) {
        String sql = "INSERT INTO sessao (id, id_pauta, data_hora_inicio, data_hora_fim, status) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, sessao.getId(), sessao.getIdPauta(), sessao.getDataHoraInicio(), sessao.getDataHoraFim(), sessao.getStatus().name());
    }

    public List<Sessao> listarTodas() {
        return jdbcTemplate.query("SELECT * FROM sessao", sessaoMapper);
    }

    public Optional<Sessao> buscarPorId(UUID id) {
        List<Sessao> result = jdbcTemplate.query("SELECT * FROM sessao WHERE id = ?", sessaoMapper, id);
        return result.stream().findFirst();
    }

    public void abrirSessoesAgendadas() {
        String sql = "UPDATE sessao SET status = 'ABERTA' WHERE status = 'AGUARDANDO' AND data_hora_inicio <= NOW()";
        jdbcTemplate.update(sql);
    }

    public List<Sessao> listarSessoesExpiradas() {
        String sql = "SELECT * FROM sessao WHERE status = 'ABERTA' AND data_hora_fim <= NOW()";
        return jdbcTemplate.query(sql, sessaoMapper);
    }

    public void finalizarSessao(UUID id) {
        String sql = "UPDATE sessao SET status = 'FINALIZADA' WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


    private RowMapper<Sessao> sessaoMapper = (ResultSet rs, int rowNum) -> {
        Sessao sessao = new Sessao();
        sessao.setId(UUID.fromString(rs.getString("id")));
        sessao.setIdPauta(UUID.fromString(rs.getString("id_pauta")));
        sessao.setDataHoraInicio(rs.getTimestamp("data_hora_inicio").toLocalDateTime());
        sessao.setDataHoraFim(rs.getTimestamp("data_hora_fim").toLocalDateTime());
        sessao.setStatus(StatusSessao.valueOf(rs.getString("status")));
        return sessao;
    };
}
