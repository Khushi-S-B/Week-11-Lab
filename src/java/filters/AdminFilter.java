
package filters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.UserService;


public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

      
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        
      
        HttpSession session = httpRequest.getSession();
        String username = (String)session.getAttribute("username");
        
        try {
            UserService us = new UserService();
            User user = us.getUser(username);
            if( !user.getRole().getRoleId().equals(1) ) {
                httpResponse.sendRedirect("notes");
                return;
            }
        }
        catch (Exception ex) {
            Logger.getLogger(AdminFilter.class.getName()).log(Level.SEVERE, "Problem accessing user: {0}", username);
            httpResponse.sendRedirect("notes");
            return;
        }
        
        chain.doFilter(request, response);
        
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void destroy() {}
    
}
