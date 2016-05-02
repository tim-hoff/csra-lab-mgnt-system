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
@Table(name = "Inventory")
public class Inventory {
    @Id
    @Column(name = "item_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer item_id;
    
    @Constraints.Required
    public String model_number;

    @Constraints.Required
    public String serial_number;

    public boolean retired;

    @Column(name = "item_type", columnDefinition = "ENUM('iPhone', 'macbook', 'raspberryPi', 'android_phone', 'iPad', 'dell_laptop', 'galaxy_tablet')")
    @Enumerated(EnumType.STRING)
    public ItemType item_type;

    public static enum ItemType {
        iPhone, 
        macbook, 
        raspberryPi, 
        android_phone, 
        iPad, 
        dell_laptop, 
        galaxy_tablet;
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

}


