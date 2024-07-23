package by.gvu.testtracingrestclient.rest;

import io.micrometer.tracing.BaggageInScope;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.filter.ServerHttpObservationFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class AddBaggageFilter extends OncePerRequestFilter implements OrderedFilter {
    private static final String fieldRemote = "testRemoteBaggage";
    private static final String fieldLocal = "testLocalBaggage";

    private final Tracer tracer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String baggagefieldRemote = request.getHeader(fieldRemote);
        if (StringUtils.isEmpty(baggagefieldRemote)) {
            baggagefieldRemote = "autoValue"+fieldRemote;
        }
        String baggagefieldLocal = "autoValue"+fieldLocal;

        log.warn("baggagefieldRemote value : [{}]", baggagefieldRemote);
        log.warn("baggagefieldLocal value : [{}]", baggagefieldLocal);

        try (BaggageInScope baggageRemote = tracer.createBaggageInScope(fieldRemote, baggagefieldRemote);
             BaggageInScope baggageLocal = tracer.createBaggageInScope(fieldLocal, baggagefieldLocal)) {
            log.warn("baggagefieldRemote was created!");
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 2;
    }
}
