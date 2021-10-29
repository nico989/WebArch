package com.example.memory_game.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class AuthFilter implements Filter {
    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    protected boolean checkUser(HttpSession session) {
        String username = (String) session.getAttribute("usernameInSession");
        ArrayList<String> users = (ArrayList<String>) filterConfig.getServletContext().getAttribute("users");
        return users.contains(username);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession(false);
        if (Objects.isNull(session) || !checkUser(session)) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.jsp");
            return;
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
        this.filterConfig = null;
    }
}