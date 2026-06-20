package com.fcs.admin.adapter.dataprovider.http.openfeign.configuration;

import feign.RequestInterceptor;
import io.micrometer.tracing.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignTracingConfig {

    @Bean
    public RequestInterceptor tracingInterceptor(Tracer tracer) {
        return requestTemplate -> {
            var span = tracer.currentSpan();
            if (span != null) {
                var context = span.context();
                requestTemplate.header("X-B3-TraceId", context.traceId());
                requestTemplate.header("X-B3-SpanId", context.spanId());
                requestTemplate.header("X-B3-Sampled", "1");
            }
        };
    }
}
