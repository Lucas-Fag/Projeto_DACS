package br.univille.apidacs2022.opentelemetry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.Configuration.SenderConfiguration;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.jaegertracing.internal.samplers.RateLimitingSampler;

@Configuration
public class JaegerConfig {
    @Bean
    public JaegerTracer jaegerTracer() {
        
        // Atenção: nunca faça isso em produção
        // SamplerConfiguration samplerConfig = new SamplerConfiguration()
        //                                         .withType(ConstSampler.TYPE)
        //                                         .withParam(1);
        
        SamplerConfiguration samplerConfig = new SamplerConfiguration()
                                                .withType(RateLimitingSampler.TYPE)
                                                .withParam(10);

        ReporterConfiguration reporter = new ReporterConfiguration()
                                            .withLogSpans(true)
                                            .withSender(new SenderConfiguration()
                                                            .withAgentHost("172.18.0.35")
                                            );

        return new io.jaegertracing
                    .Configuration("dacsapi-Lucas_Fagundes")
                    .withSampler(samplerConfig)
                    .withReporter(reporter)
                    .getTracer();
    }
}
