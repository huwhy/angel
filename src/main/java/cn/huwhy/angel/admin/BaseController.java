package cn.huwhy.angel.admin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Strings;

public abstract class BaseController {
    protected final Logger logger = LogManager.getLogger(getClass());

    protected void printResponse(HttpServletResponse response, String message) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            if (!Strings.isNullOrEmpty(message))
                writer.write(message);
        } catch (Exception e) {
            logger.error("WxMpEndpointController - printResponse - exception", e);
        } finally {
            try {
                if (null != writer)
                    writer.close();
            } catch (Exception ignore) {
            }
        }
    }
}
