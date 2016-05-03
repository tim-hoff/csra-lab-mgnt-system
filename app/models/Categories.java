package models;

import java.util.*;

import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;
import play.db.jpa.*;
import play.db.jpa.JPA;
import play.libs.Scala;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Index;


@Entity
@Table(name = "Categories")
public class Categories {

	@Id
	public String category_name;

	public String category_color;

    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
    }

	public static Categories findById(String id) {
		return JPA.em().find(Categories.class, id);
	}

	public static List<Categories> categories() {
		List<Categories> data = JPA.em().createQuery("SELECT c FROM Categories c", Categories.class).getResultList();
		return data;
	}
}