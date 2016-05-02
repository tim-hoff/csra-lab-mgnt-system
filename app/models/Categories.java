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

    public void save() {
        JPA.em().persist(this);
    }

    public void delete() {
        JPA.em().remove(this);
    }

}