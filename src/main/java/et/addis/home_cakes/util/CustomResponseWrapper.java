package et.addis.home_cakes.util;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Created by Fassil on 03/11/20.
 */
public class CustomResponseWrapper extends HttpServletResponseWrapper {
    public CustomResponseWrapper(HttpServletResponse response) {
        super(response);
    }
}
