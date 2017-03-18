package cn.huwhy.angel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;

@Component
public class ExceptionResolver implements HandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        logger.error(ex.getMessage(), ex);
        return null;
    }

    /**
     * AJAX请求
     */
    public boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    /**
     * Json请求
     */
    public boolean isJsonRequest(HttpServletRequest request) {
        String type = request.getHeader("Accept");
        if (Strings.isNullOrEmpty(type))
            return false;
        return type.toLowerCase().contains("application/json");
    }

    public boolean isAjax(HttpServletRequest request) {
        return isAjaxRequest(request) || isJsonRequest(request);
    }

}
