package controllers;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.db.jpa.*;

import views.html.history.*;

import models.*;

import org.pac4j.core.profile.CommonProfile;
import org.pac4j.play.java.RequiresAuthentication;
import org.pac4j.play.java.UserProfileController;


public class HistoryController extends UserProfileController<CommonProfile> {
	@RequiresAuthentication(clientName = "CasClient")
	@Transactional
	public Result index() {
		if(!checkPrivilegesAdmin())
		{
			flash("success", "Insufficient Privileges");
			return redirect("/home");
		}
		
		return ok(index.render());
	}

	@Transactional
	public Result show(Integer id) {
		if(!checkPrivilegesAdmin())
		{
			flash("success", "Insufficient Privileges");
			return redirect("/home");
		}
		
		return ok(show.render(History.findById(id)));
	}
	
	//This function returns false if current user does not possess the role Admin or SuperAdmin
	public boolean checkPrivilegesAdmin()
	{
		CommonProfile profile = getUserProfile();
		//if you don't have admin role then redirect back to dashboard
		if(User.findById(profile.getId()).role == User.Role.Admin ||  User.findById(profile.getId()).role == User.Role.SuperAdmin)
		{
			return true;
		}
		return false;
	}
}