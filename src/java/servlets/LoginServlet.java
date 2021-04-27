package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;
import utilities.CookieUtil;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate(); 
        
       
        Cookie[] cookies = request.getCookies();
        String username = CookieUtil.getCookieValue(cookies, "username");
        request.setAttribute("username", username);
        
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
      
        Cookie cookie = new Cookie("username", username);
        cookie.setMaxAge(60 * 60 * 24 * 365 * 3);
        response.addCookie(cookie);
        
   
        AccountService as = new AccountService();
        String path = getServletContext().getRealPath("/WEB-INF");
        User user = as.login(username, password, path);
        
        if (user == null) {
            request.setAttribute("username", username);
            request.setAttribute("message", "Username or password is wrong");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        
        if (user.getRole().getRoleId() == 1) {
            response.sendRedirect("admin");
        } else {
            response.sendRedirect("notes");
        }
    }
}
