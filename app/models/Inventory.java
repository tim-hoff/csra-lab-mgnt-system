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

    @Constraints.Required
    @Column(name="item_type", columnDefinition="ENUM('macbook', 'iphone')")
    public String item_type;

    public static enum ItemType {
        macbook,
        iphone
    }
 
    public static Inventory findById(Integer id) {
        return JPA.em().find(Inventory.class, id);
    }

    public void delete() {
        JPA.em().remove(this);
    }

   
    public static Inventorys items() {
            List<Inventory> data = JPA.em()
            .createQuery("SELECT c FROM Inventory c", Inventory.class)
            .getResultList();
        return new Inventorys (data);
    }

    public static class Inventorys {

        private final List<Inventory> list;

        public Inventorys (List<Inventory> list) {
            this.list = list;
        }

        public List<Inventory> getList() {
            return list;
        }

        public int getSize() {
            return list.size();
        }
    }
}


