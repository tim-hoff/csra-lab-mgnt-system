package models;

import java.util.*;

import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;
import play.db.jpa.*;
import org.hibernate.annotations.Type;
import org.joda.time.*;
import org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime;

@Entity 
public class Inventory {
    @Id
    public Integer item_id;
    
    @Constraints.Required
    public String model_number;

    @Constraints.Required
    public String serial_number;

    public boolean retired;

    @Column(name="item_type", columnDefinition="ENUM('macbook', 'iphone')")
    public String item_type;

    public static enum ItemType {
        macbook,
        iphone
    }
    
    public static Inventory findById(Integer id) {
        return JPA.em().find(Inventory.class, id);
    }

    public boolean available(){
        if(!retired && (taken_date == null) && (rented_by == null)) { return true; }
        return false;
    }

    public void update(Integer id) {
        this.item_id = id;
        JPA.em().merge(this);
    }

    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_rented_by")
    public User rented_by;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime taken_date;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime return_date;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime last_notified;
    
    public static List<Inventory> items() {
        List<Inventory> data = JPA.em()
        .createQuery("SELECT c FROM Inventory c", Inventory.class)
        .getResultList();
        return data;
    }

    public void checkin() {
        rented_by = null;
        save();
    }

    public void checkout(String user_id, DateTime rd) {
        User usr = User.findById(user_id);
        return_date = rd;
        rented_by = usr;
        save();
    }
}


