package com.br.voting.filter;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class ScheduledTraceAspect {

    private static final String TRACE_ID = "traceId";

    @Pointcut("@annotation(scheduled)")
    public void scheduledMethod(Scheduled scheduled) {}

    @Before("scheduledMethod(scheduled)")
    public void beforeScheduledMethod(Scheduled scheduled) {
        String traceId = UUID.randomUUID().toString();
        MDC.put(TRACE_ID, traceId);
    }

    @After("scheduledMethod(scheduled)")
    public void afterScheduledMethod(Scheduled scheduled) {
        MDC.remove(TRACE_ID);
    }
}
