package models;

import java.util.*;

import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;
import play.db.jpa.*;
import org.hibernate.annotations.Type;

import scala.collection.JavaConversions.*;
import scala.collection.JavaConverters.*;
import scala.collection.convert.*;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Seq;

import scalaj.collection.*;

@Entity
public class Types {

	@Id
	public String type_name;

	public static List<Types> all_types(){
		List<Types> data = JPA.em()
		.createQuery("SELECT t.type_name FROM Types t", Types.class)
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