package models;

import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import play.data.format.*;
import play.data.validation.*;
import play.db.jpa.*;

import java.util.stream.Collectors;

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

  public static List<User> admins(){
    List<User> data = users().stream().filter(user -> user.role == Role.Admin || user.role == Role.SuperAdmin ).collect(Collectors.toList());
    return data;
}

public List<Ticket> getTickets(){
    List<Ticket> data = Ticket.tickets().stream().filter(ticket -> ticket.assigned_to.compareTo(user_id) == 0).collect(Collectors.toList());
    return data;
}

public int priorityCount(Ticket.Priority p, int months ){
    long count = getTickets().stream().filter(t -> t.priority == p && t.inRange(months)).count();
    return (int) count;
}


}


