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

    public String description;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date date_created;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date last_updated;

    @Constraints.Required
    @Column(name="priority", columnDefinition="ENUM('Low', 'Normal', 'High')")
    public String priority;

    public static enum Priority {
        Low,
        Normal,
        High
    }
    
    @Constraints.Required
    @Column(name="state", columnDefinition="ENUM('Pending', 'Resolved')")
    public String state;

    public static enum State {
        Pending,
        Resolved
    }

    public static Ticket findById(Integer id) {
        return JPA.em().find(Ticket.class, id);
    }
    public String ticketID(){
    	return Integer.toString(ticketID);
    };

    public void delete() {
        JPA.em().remove(this);
    }

     public static Tickets tickets() {
            List<Ticket> data = JPA.em()
            .createQuery("SELECT c FROM Ticket c", Ticket.class)
            .getResultList();
        return new Tickets (data);
    }

    public static class Tickets {

        private final List<Ticket> list;

        public Tickets (List<Ticket> list) {
            this.list = list;
        }

        public List<Ticket> getList() {
            return list;
        }

        public int getSize() {
            return list.size();
        }
    }
}


