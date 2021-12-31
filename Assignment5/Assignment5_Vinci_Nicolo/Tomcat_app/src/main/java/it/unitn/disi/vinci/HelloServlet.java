package it.unitn.disi.vinci;

import it.unitn.disi.vinci.services.guest.GuestService;
import it.unitn.disi.vinci.exceptions.EntityNotFoundException;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        GuestService ss = null;
        try {
            ss = ServiceLocator.getInstance().lookup(GuestService.class);
            System.out.println(ss);
            System.out.println(ss.readById(1).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        try {
            out.println("<h1>" + ss.readById(1).getName() + "</h1>");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        out.println("</body></html>");
    }

    public void destroy() {
    }
}