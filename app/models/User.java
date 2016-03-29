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

    // @Enumerated
    // public State state;

    // public static enum State {
    //     Pending,
    //     Resolved
    // }

    // @Enumerated
    // public Priority priority;



    // public static enum Priority {
    //     Low,
    //     Normal,
    //     High
    // }
 
    public static User findById(String id) {
        return JPA.em().find(User.class, id);
    }

    public void delete() {
        JPA.em().remove(this);
    }
}


