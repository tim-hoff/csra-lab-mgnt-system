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
public class History {

    @Id
    public Integer item_id;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime taken_date;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime return_date; 

    @Formats.DateTime(pattern="yyyy-MM-dd")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime last_updated;
   

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


