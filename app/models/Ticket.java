package models;

import java.util.*;

import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;
import play.db.jpa.*;

@Entity 
public class Ticket {

    @Id
    public Integer ticketID;
    
    @Constraints.Required
    public String name;

    public String assigned_to;

    public String created_for;

    public String description;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date date_created;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date last_updated;

    @Column(name="priority", columnDefinition="ENUM('Low', 'Normal', 'High')")
    public String priority;
    
    @Column(name="state", columnDefinition="ENUM('Pending', 'Resolved')")
    public String state;

    public static enum Priority {
        Low,
        Normal,
        High
    }

    public static enum State {
        Pending,
        Resolved
    }

    public void update(Integer id) {
        this.ticketID = id;
        JPA.em().merge(this);
    }

    public static Ticket findById(Integer id) {
        return JPA.em().find(Ticket.class, id);
    }
    public String ticketID(){
    	return Integer.toString(ticketID);
    };

    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
    }

    public static List<Ticket> tickets() {
     List<Ticket> data = JPA.em()
     .createQuery("SELECT c FROM Ticket c", Ticket.class)
     .getResultList();
     return data;
 }

}


