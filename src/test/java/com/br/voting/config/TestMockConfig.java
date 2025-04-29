package com.br.voting.config;

import com.br.voting.broker.SessaoFinalizadaProducer;
import com.br.voting.mapper.AssociadoMapper;
import com.br.voting.mapper.PautaMapper;
import com.br.voting.mapper.SessaoMapper;
import com.br.voting.mapper.VotoMapper;
import com.br.voting.repository.AssociadoRepository;
import com.br.voting.repository.PautaRepository;
import com.br.voting.repository.SessaoRepository;
import com.br.voting.repository.VotoRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestMockConfig {

    @Bean
    public AssociadoRepository associadoRepository() {
        return mock(AssociadoRepository.class);
    }

    @Bean
    public AssociadoMapper associadoMapper() {
        return mock(AssociadoMapper.class);
    }

    @Bean
    public PautaRepository pautaRepository() {
        return mock(PautaRepository.class);
    }

    @Bean
    public PautaMapper pautaMapper() {
        return mock(PautaMapper.class);
    }

    @Bean
    public SessaoRepository sessaoRepository() {
        return mock(SessaoRepository.class);
    }

    @Bean
    public SessaoMapper sessaoMapper() {
        return mock(SessaoMapper.class);
    }

    @Bean
    public VotoRepository votoRepository() {
        return mock(VotoRepository.class);
    }

    @Bean
    public VotoMapper votoMapper() {
        return mock(VotoMapper.class);
    }

    @Bean
    public SessaoFinalizadaProducer sessaoFinalizadaProducer() {
        return mock(SessaoFinalizadaProducer.class);
    }
}