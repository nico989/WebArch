package com.example.chat_system.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class AuthFilter implements Filter {
    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession(false);
        if (Objects.nonNull(session) && Objects.nonNull(session.getAttribute("authenticated")) && (boolean) session.getAttribute("authenticated")) {
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendError(javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED);
        }

    }

    public void destroy() {
        this.filterConfig = null;
    }
}
