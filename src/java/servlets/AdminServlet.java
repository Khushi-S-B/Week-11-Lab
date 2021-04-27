package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.User;
import services.UserService;

public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserService us = new UserService();
        String action = request.getParameter("action");
        if (action != null && action.equals("view")) {
            String selectedUsername = request.getParameter("selectedUsername");
            try {
                User user = us.getUser(selectedUsername);
                request.setAttribute("selectedUser", user);
            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        List<User> users = null;        
        try {
            users = us.getAll(); 
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");

        UserService us = new UserService();

        try {
            if (action.equals("delete")) {
                String selectedUsername = request.getParameter("selectedUsername");
                us.delete(selectedUsername);
            } else if (action.equals("edit")) {
                us.update(username, password, firstname, lastname, email);
            } else if (action.equals("add")) {
                us.insert(username, password, firstname, lastname, email);
            }
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Whoops.  Could not perform that action.");
        }
        
        List<User> users = null;
        try {
            users = us.getAll();
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        request.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }
}
