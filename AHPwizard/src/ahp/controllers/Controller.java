package ahp.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ahp.model.AhpModel;

@org.springframework.stereotype.Controller
@org.springframework.web.bind.annotation.RequestMapping(value = { "/index",
		"new", "evaluate" })
public class Controller {
	private static Context envCtx;
	private static DataSource ds;
	static {
		try {
			envCtx = (Context) new InitialContext().lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("jdbc/ahp");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public ModelAndView doGetIndex(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("index", "message", "a test");
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView doGetview(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("new");
	}

	// @RequestParam String dn
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ModelAndView doPostview(HttpSession session, AhpModel project, HttpServletRequest request,
			HttpServletResponse response) {
		

		String criteriaLabels[] = new String[7];
		criteriaLabels[0] = "Cost";
		criteriaLabels[1] = "Travel Time";
		criteriaLabels[2] = "Safety";
		criteriaLabels[3] = "Pollution";
		criteriaLabels[4] = "Noise";
		criteriaLabels[5] = "Accessabilty";
		criteriaLabels[6] = "Energy Consumption";
		
		Arrays.sort(criteriaLabels);

		// alternative labels
		String alternativeLabels[] = new String[5];
		alternativeLabels[0] = "Rail";
		alternativeLabels[1] = "Sea";
		alternativeLabels[2] = "Road";
		alternativeLabels[3] = "Air";
		alternativeLabels[4] = "Jet Ski";
		
		Arrays.sort(alternativeLabels);

		project.setCriteriaLabels(criteriaLabels);
		
		project.setAlternativeLabels(alternativeLabels);
		
		session.setAttribute("currentProject", project);
		
		project.setStatus("ready");
		
		return new ModelAndView("new", "model", project);
		
	}
	
	// @RequestParam String dn
		@RequestMapping(value ="/evaluate", method = RequestMethod.POST)
		public ModelAndView doPostEvaluate(HttpSession session, HttpServletRequest request,
				HttpServletResponse response) {
			
			
			AhpModel project = (AhpModel) session.getAttribute("currentProject");
			
			project.setStatus("complete");
			
			return new ModelAndView("new", "model", project);
			
		}

	
	
	
	
	
	
	
	
	
	
	/*
	String message = null;
	Connection connection = null;
	try {
		connection = ds.getConnection();
		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO project (name, description) values(?,?)");
		ps.setString(1, project.getGoalName());
		ps.setString(2, project.getGoalDescription());
		ps.execute();
	} catch (SQLException e) {
		e.printStackTrace();
		message = e.getMessage();
	} finally {
		closeConnection(connection);
	}
	return new ModelAndView("new", "message",
			message == null ? "Project Created" : message);
	*/
	private void closeConnection(Connection connection) {

		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}
