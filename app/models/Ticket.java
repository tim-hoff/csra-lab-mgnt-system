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


    @Column(name="priority", columnDefinition="ENUM('Low', 'Normal', 'High')")
    public String priority;

    public static enum Priority {
        Low,
        Normal,
        High
    }

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
}


