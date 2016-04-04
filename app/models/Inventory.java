package models;

import java.util.*;

import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;
import play.db.jpa.*;

@Entity 
public class Inventory {
    @Id
    public Integer item_id;
    
    @Constraints.Required
    public String model_number;

    @Constraints.Required
    public String serial_number;

    public boolean retired;

    public boolean available;

    @Column(name="item_type", columnDefinition="ENUM('macbook', 'iphone')")
    public String item_type;

    public static enum ItemType {
        macbook,
        iphone
    }
    
    public static Inventory findById(Integer id) {
        return JPA.em().find(Inventory.class, id);
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
    public Date taken_date;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date return_date;
    
    public static List<Inventory> items() {
        List<Inventory> data = JPA.em()
        .createQuery("SELECT c FROM Inventory c", Inventory.class)
        .getResultList();
        return data;
    }

}


