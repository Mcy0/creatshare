package com.mcy.web.filter;



import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

//编码过滤器
@WebFilter(filterName = "EncodingFilter",urlPatterns = "/*",initParams = {@WebInitParam(name="charset",value = "UTF-8"),
        @WebInitParam(name="originalCharset",value = "ISO-8859-1")
})
public class EncodingFilter implements Filter {

    //编码字符集
    private String charset = null;
    //出是字符集
    private String originalCharset = null;
    public void destroy() {
        charset = null;
        originalCharset = null;
    }
    //过滤
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        String method = request.getMethod();
        if (method.equalsIgnoreCase("post"))
        {
            request.setCharacterEncoding(charset);
        }
        else if (method.equalsIgnoreCase("get"))
        {
            HttpServletRequest myRequest = new MyRequest(request,charset,originalCharset);
        }

        chain.doFilter(req, resp);
    }
    
    public void init(FilterConfig config) throws ServletException {
        charset = config.getInitParameter("charset");
        originalCharset = config.getInitParameter("originalCharset");
    }

}
class MyRequest extends HttpServletRequestWrapper{
    private static Logger logger = Logger.getLogger(MyRequest.class);
    private HttpServletRequest request;
    private String charset;
    private String originalCharset;
    //手动编码只进行一次
    private boolean hasEncode;
    public MyRequest(HttpServletRequest request,String charset, String originalCharset) {
        super(request);
        this.request = request;
        this.charset = charset;
        this.originalCharset = originalCharset;
        hasEncode = false;
    }

    @Override
    public String getParameter(String name) {
        Map<String,String[]> parameterMap = request.getParameterMap();
        String[] values = parameterMap.get(name);
        if (values == null)
        {
            return null;
        }
        try {
            values[0] = new String(values[0].getBytes(originalCharset),charset);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return values[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return super.getParameterMap();
    }

    @Override
    public String[] getParameterValues(String name) {
        Map<String,String[]> parameterMap = request.getParameterMap();
        if (!hasEncode)
        {
            for (String parameterName :
                    parameterMap.keySet()) {
                String[] parameterValues = parameterMap.get(parameterName);
                for (int i = 0; i < parameterValues.length; i++) {
                    try {
                        parameterValues[i] = new String(parameterValues[i].getBytes(originalCharset),charset);
                    } catch (UnsupportedEncodingException e) {
                        logger.error(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
            
            hasEncode = true;
        }
        return super.getParameterValues(name);
    }
}