package com.br.voting.service;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MensagemService {

    private final MessageSource messageSource;

    public MensagemService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMensagem(String codigo) {
        return messageSource.getMessage(codigo, null, LocaleContextHolder.getLocale());
    }
}
