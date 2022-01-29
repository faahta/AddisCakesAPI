package et.addis.home_cakes.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Fassil on 01/11/20.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LogFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(Filter.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // we just need to put this UUID_KEY inside the log file xml we have, since MDC
        // is putting the value here. i.e no need to call it anywhere and the UUID will be generated for requests/response only
        MDC.put(LogConstants.UUID_KEY, UUID.randomUUID().toString());

        HttpServletResponse customResponse = (HttpServletResponse) response;
        CustomResponseWrapper customWrapper = new CustomResponseWrapper(customResponse);
        customWrapper.addHeader(LogConstants.TRANSACTION_ID_KEY, MDC.get(LogConstants.UUID_KEY));
        chain.doFilter(request,customWrapper);

        MDC.remove(LogConstants.UUID_KEY);
    }
}
