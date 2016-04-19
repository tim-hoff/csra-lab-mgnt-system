package models;

import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import play.data.format.*;
import play.data.validation.*;
import play.db.jpa.*;

@Entity 
public class User {
    @Id
    @Column(name="user_id", nullable=false)
    public String user_id;
    
    @Column(nullable=false)
    public String first_name;
    
    @Column(nullable=false)
    public String last_name;

    public String email;

    public String note;

    public boolean active;

    @Column(name="role", columnDefinition="ENUM('Admin', 'Student', 'SuperAdmin')")
    @Enumerated(EnumType.STRING)
    public Role role;

    public static enum Role {
        Admin,
        Student,
        SuperAdmin
    }

    public static User findById(String id) {
        return JPA.em().find(User.class, id);
    }

    public void delete() {
        JPA.em().remove(this);
    }

    public void save() {
        JPA.em().persist(this);
    }
    //creates a new entry if one does not already exist
    public User(String id) {
    	//check for existing user
    	if(JPA.em().find(User.class, id) == null) {
    		this.user_id = id;
    		this.role = Role.SuperAdmin;
    	}
    }
    public void update(String id) {
        this.user_id = id;
        JPA.em().merge(this);
    }

    public static List<User> users(){
      List<User> data = JPA.em()
      .createQuery("SELECT c FROM User c", User.class)
      .getResultList();
      return data;
  }
}


