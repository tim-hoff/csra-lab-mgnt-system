package models;

import java.util.*;

import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;
import play.db.jpa.*;

@Entity 
public class User {
    @Id
    public String user_id;
    
    @Constraints.Required
    public String first_name;
    
    @Constraints.Required
    public String last_name;

    @Constraints.Required
    public String email;

    public String note;

    public boolean active;

    @Constraints.Required
    @Column(name="role", columnDefinition="ENUM('Admin', 'Student')")
    public String role;

    public static enum Role {
        Admin,
        Student
    }
 
    public static User findById(String id) {
        return JPA.em().find(User.class, id);
    }

    public void delete() {
        JPA.em().remove(this);
    }
   
    public static Users users() {
            List<User> data = JPA.em()
            .createQuery("SELECT c FROM User c", User.class)
            .getResultList();
        return new Users (data);
    }

    public static class Users {

        private final List<User> list;

        public Users (List<User> list) {
            this.list = list;
        }

        public List<User> getList() {
            return list;
        }

        public int getSize() {
            return list.size();
        }
    }
}


