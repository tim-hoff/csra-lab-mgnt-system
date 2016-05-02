package models;

import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;
import play.data.validation.Constraints.Required;

import play.db.jpa.*;

import play.Logger;


import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import org.joda.time.*;
import org.joda.*;
import org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime;
@Entity
@Table(name = "Ticket")
public class Ticket {
	@Id
	@Column(name = "ticketID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer ticket_id;

	@Column(name = "name", nullable = false)
	public String name;

	public String assigned_to;

	public String created_for;

	public String description;

	@Generated(GenerationTime.ALWAYS)
	@Formats.DateTime(pattern = "yyyy-MM-dd")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime date_created;

	@Generated(GenerationTime.ALWAYS)
	@Formats.DateTime(pattern = "yyyy-MM-dd")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime last_updated;

	@Column(name = "priority", columnDefinition = "ENUM('Low', 'Normal', 'High')")
	@Enumerated(EnumType.STRING)
	public Priority priority;

	public String category;

	@Column(name = "state", columnDefinition = "ENUM('Pending', 'Resolved')")
	@Enumerated(EnumType.STRING)
	public State state;

	/*@Column(name = "admin", columnDefinition = "ENUM(VARCHAR(40))")
	@Enumerated(EnumType.STRING)
	public State admin;*/


	public static enum Priority {
		Low, Normal, High
	}


	public static enum State {
		Pending, Resolved
	}

	/*public static enum Admin {
		
		admin1, admin2, admin3, admin4
		//User."user_id";
	}*/

	public void update(Integer id) {
		this.ticket_id = id;
		JPA.em().merge(this);
	}

	public static Ticket findById(Integer id) {
		return JPA.em().find(Ticket.class, id);
	}

	public void save() {
		JPA.em().persist(this);
	}

	public void delete() {
		JPA.em().remove(this);
	}

	public static List<Ticket> tickets() {
		List<Ticket> data = JPA.em().createQuery("SELECT c FROM Ticket c", Ticket.class).getResultList();
		return data;
	}

	public static int categoryCount(String cat, Integer months) {
		long list = tickets().stream().filter(c -> (isInMonthRange(months, c.date_created)) && c.category.equals(cat))
		.count();

		return (int) list;
	}

	public static boolean isInMonthRange(Integer months, DateTime date){
		DateTime today = new DateTime();
		DateTime beginDate = today.minusMonths(months);

		return date.isAfter(beginDate);
	}

	public boolean inRange(Integer months){
		DateTime today = new DateTime();
		DateTime beginDate = today.minusMonths(months);

		return date_created.isAfter(beginDate);
	}

	
	//get user ticket assignment count


}
