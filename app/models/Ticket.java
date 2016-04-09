package models;

import java.util.*;

import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;
import play.data.validation.Constraints.Required;

import play.db.jpa.*;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Index;

@Entity 
@Table(name = "Ticket")
public class Ticket {
    @Id
    @Column(name="ticketID", nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    public Integer ticket_id;

    @Column(name="name", nullable=false)
    public String name;

    public String assigned_to;

    public String created_for;

    public String description;

    @Generated(GenerationTime.ALWAYS) 
    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date date_created;

    @Generated(GenerationTime.ALWAYS) 
    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date last_updated;
    
    @Column(name="priority", columnDefinition="ENUM('Low', 'Normal', 'High')")
    @Enumerated(EnumType.STRING)
    public Priority priority;
    
    @Column(name="state", columnDefinition="ENUM('Pending', 'Resolved')")
    @Enumerated(EnumType.STRING)
    public State state;

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
        this.ticket_id = id;
        JPA.em().merge(this);
    }

    public static Ticket findById(Integer id) {
        return JPA.em().find(Ticket.class, id);
    }

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

   public static List<Ticket> filteredTickets(){
        List<Ticket> list = tickets();
        return list;
   }

}


