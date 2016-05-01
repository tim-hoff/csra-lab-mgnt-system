package models;

import java.util.*;

import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;
import play.db.jpa.*;
import org.hibernate.annotations.Type;

@Entity
public class Types {

	@Id
	public String type_name;

	public static List<Types> all_types(){
		List<Types> data = JPA.em()
		.createQuery("SELECT c FROM Types c", Types.class)
		.getResultList();
		return data;
	}


    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
    }

}