package services;

import dataaccess.UserDB;
import models.User;
import java.util.List;

public class UserService {

    private UserDB userDB;

    public UserService() {
        userDB = new UserDB();
    }

    public User getUser(String username) throws Exception {
        return userDB.getUser(username);
    }

    public List<User> getAll() throws Exception {
        return userDB.getAll();
    }

    public int update(String username, String password, String firstname, String lastname, String email) throws Exception {
        User user = getUser(username);
        user.setPassword(password);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        return userDB.update(user);
    }

    public int delete(String username) throws Exception {
        User deletedUser = userDB.getUser(username);
      
        if (deletedUser.getUsername().equals("admin")) {
            return 0;
        }
        return userDB.delete(deletedUser);
    }

    public int insert(String username, String password, String firstname, String lastname, String email) throws Exception {
        User user = new User(username, password, email);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        return userDB.insert(user);
    }

    public User getByEmail(String email) {
        return userDB.getUserByEmail(email);
    }

    public User getByUUID(String uuid) {
        return userDB.getUserByUUID(uuid);
    }
}
