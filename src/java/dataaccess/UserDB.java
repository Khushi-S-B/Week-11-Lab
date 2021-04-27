package dataaccess;

import utilities.DBUtil;
import models.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Role;

public class UserDB {

    public int insert(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
          
            Role role = em.find(Role.class, 3);
            user.setRole(role);
            
            trans.begin();
            em.persist(user);
            trans.commit();
            return 1;
        } catch (Exception ex) {
            trans.rollback();
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, "Cannot insert " + user.toString(), ex);
            throw new Exception("Error inserting user");
        } finally {
            em.close();
        }
    }

    public int update(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
            return 1;
        } catch (Exception ex) {
            trans.rollback();
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, "Cannot update " + user.toString(), ex);
            throw new Exception("Error updating user");
        } finally {
            em.close();
        }
    }

    public List<User> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            List<User> users = em.createNamedQuery("User.findAll", User.class).getResultList();
            return users;                
        } finally {
            em.close();
        }
    }

    public User getUser(String username) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            User user = em.find(User.class, username);
            return user;
        } finally {
            em.close();
        }
    }

    public int delete(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.remove(em.merge(user));
            trans.commit();
            return 1;
        } catch (Exception ex) {
            trans.rollback();
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, "Cannot delete " + user.toString(), ex);
            throw new Exception("Error deleting user");
        } finally {
            em.close();
        }
    }
    
    
     public User getUserByEmail(String email){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
         User user = em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email).getSingleResult();
         return user;
     }
    

    public User getUserByUUID(String uuid){
         EntityManager em = DBUtil.getEmFactory().createEntityManager();
         User user = em.createNamedQuery("User.findByResetPasswordUUID", User.class).setParameter("resetPasswordUUID", uuid).getSingleResult();
         return user;
    }
}
