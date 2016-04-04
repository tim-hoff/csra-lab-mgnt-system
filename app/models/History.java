package models;

import java.util.*;

import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;
import play.db.jpa.*;

@Entity 
public class History {

    @Id
    public Integer item_id;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date taken_date;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date return_date; 

    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date last_updated;
   

    public static History findById(Integer id) {
        return JPA.em().find(History.class, id);
    }

    public void delete() {
        JPA.em().remove(this);
    }

    public static List<History> histories() {
       List<History> data = JPA.em()
       .createQuery("SELECT c FROM History c", History.class)
       .getResultList();
       return data;
   }

}


