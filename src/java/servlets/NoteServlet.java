package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Note;
import models.User;
import services.NoteService;
import services.UserService;

public class NoteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        NoteService ns = new NoteService();
        UserService us = new UserService();

        try {
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");
            List<Note> notes = ns.getAll(username);
            User user = us.getUser(username);
            request.setAttribute("notes", notes);
            request.setAttribute("user", user);
            
        } catch (Exception ex) {
            Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }

        String action = request.getParameter("action");
        if (action != null && action.equals("view")) {
            try {
                int id = Integer.parseInt(request.getParameter("noteId"));
                Note note = ns.get(id);
                request.setAttribute("selectedNote", note);
            } catch (Exception ex) {
                Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        getServletContext().getRequestDispatcher("/WEB-INF/notes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        NoteService ns = new NoteService();

        
        String action = request.getParameter("action");
        String noteId = request.getParameter("noteId");
        String title = request.getParameter("title");
        String contents = request.getParameter("contents");

        try {
            switch (action) {
                case "create":
                    ns.insert(title, contents, username);
                    break;
                case "update":
                    ns.update(Integer.parseInt(noteId), title, contents);
                    break;
                case "delete":
                    ns.delete(Integer.parseInt(noteId));
            }
            request.setAttribute("message", action);
        } catch (Exception ex) {
            Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }

        try {
            List<Note> notes = ns.getAll(username);
            request.setAttribute("notes", notes);
        } catch (Exception ex) {
            Logger.getLogger(NoteServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/notes.jsp").forward(request, response);
    }

}
