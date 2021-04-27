package servlets;

import dataaccess.UserDB;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;

public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        try {
            UserDB userDB = new UserDB();
            String uuid = request.getParameter("uuid");
            User user = userDB.getUserByUUID(uuid);

            if (user != null) {
                session.setAttribute("uuid", uuid);
                getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
            }

        } catch (Exception e) {
            getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AccountService accountService = new AccountService();
        HttpSession session = request.getSession();
        String uuid = (String)session.getAttribute("uuid");
        String action = request.getParameter("action");
        String password = request.getParameter("nPass");

        if (action.equals("resetPassword")) {
            if (uuid != null) {
                boolean t = accountService.changePassword(uuid, password);

                if (t) {
                    request.setAttribute("message", "Password has been changed");
                    getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                } else {
                    request.setAttribute("message", "uuid is invalid");
                    getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("message", "uuid is null");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
        }

        if (action.equals("forgotPassword")) {
            String path = getServletContext().getRealPath("/WEB-INF");
            String url = request.getRequestURL().toString();
            Boolean t = accountService.resetPassword(request.getParameter("email_address"), path, url);

            if (t) {
                String message = "Reset email send";
                request.setAttribute("message", message);
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            } else {
                String message = "Email does not exist";
                request.setAttribute("message", message);
                getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
            }
        }

    }

}
