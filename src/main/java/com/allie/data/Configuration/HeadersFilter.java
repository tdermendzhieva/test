package com.allie.data.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by jacob.headlee on 10/19/2016.
 */
@Configuration
public class HeadersFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(HeadersFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //get the headers set them in the MDC
        //if no headers reject
        try {
            String corrId = ((HttpServletRequest) request).getHeader("x-allie-correlation-id");
            String reqId = ((HttpServletRequest) request).getHeader("x-allie-request-id");
            if (corrId != null && !corrId.isEmpty() && reqId != null && !reqId.isEmpty()) {
                MDC.put("correlation-id", corrId);
                MDC.put("request-id", reqId);
                chain.doFilter(request, response);
            } else {
                logger.info("Bad request: invalid headers: x-allie-correlation-id:" + corrId + " x-allie-request-id:" + reqId);
                ((HttpServletResponse) response).sendError(400, "Missing Headers");
            }
        } finally {
            MDC.clear();
        }
    }

    @Override
    public void destroy() {}
}
