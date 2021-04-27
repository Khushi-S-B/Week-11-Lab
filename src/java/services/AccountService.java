package services;

import dataaccess.UserDB;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;


public class AccountService {

    public boolean resetPassword(String email, String path, String url) {
        String uuid = UUID.randomUUID().toString();
        UserDB userDB = new UserDB();

        try {
            User user = userDB.getUserByEmail(email);
            if (email.equals(user.getEmail())) {
                
                Logger.getLogger(AccountService.class.getName()).log(Level.INFO, "Send reset to {0}", email);

                String to = user.getEmail();
                String subject = "NoteKeeper Password Reset";
                String template = path + "/email_templates/resetpassword.html";
                
                String link = url + "?uuid=" + uuid;
                
                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("username", user.getUsername());
                tags.put("link", link);
                
                
                user.setResetPasswordUUID(uuid);
                userDB.update(user);

               
                String escape = "{{";
                GmailService.sendMailTemplate(to, subject, template, tags,escape);
                
                return true;
            }
        } catch (Exception e) {

            Logger.getLogger(AccountService.class.getName()).log(Level.INFO, "can't send reset passsword email", email);
        }
       
        return false;
    }

    public boolean changePassword(String uuid, String password) {
        UserService us = new UserService();
        try {
            User user = us.getByUUID(uuid);
            user.setPassword(password);
            user.setResetPasswordUUID(null);
            UserDB ur = new UserDB();
            ur.update(user);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public User login(String username, String password, String path) {
        UserDB userDB = new UserDB();

        try {
            User user = userDB.getUser(username);
            if (password.equals(user.getPassword())) {
                
                Logger.getLogger(AccountService.class.getName()).log(Level.INFO, "Successful login by {0}", username);

                String to = user.getEmail();
                String subject = "NoteKeeper Login";
                String template = path + "/email_templates/login_email.html";

                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("date", (new java.util.Date()).toString());

                
                String escape = "[[";
                GmailService.sendMailTemplate(to, subject, template, tags,escape);

                return user;
            }
        } catch (Exception e) {

            Logger.getLogger(AccountService.class.getName()).log(Level.INFO, "exception thrown cant login", username);
            
        }

        return null;
    }
}
