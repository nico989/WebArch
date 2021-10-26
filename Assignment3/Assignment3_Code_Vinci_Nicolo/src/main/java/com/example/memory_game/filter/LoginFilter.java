package com.example.memory_game.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public final class LoginFilter implements Filter {
    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("apply filter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession(false);
        if (Objects.nonNull(session)) {
            String usernameInSession = (String) session.getAttribute("usernameInSession");
            ServletContext context = filterConfig.getServletContext();
            ArrayList<String> users = (ArrayList<String>) context.getAttribute("users");
            if (users.contains(usernameInSession)) {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/userPage.jsp");
                return;
            }
        } else {
            httpServletRequest.getSession(true);
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() +  "/login.jsp");
            return;
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
        this.filterConfig = null;
    }
}
