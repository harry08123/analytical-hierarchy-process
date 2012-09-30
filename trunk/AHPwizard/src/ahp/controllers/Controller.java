package ahp.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ahp.model.Project;

@org.springframework.stereotype.Controller
@org.springframework.web.bind.annotation.RequestMapping(value = { "/index",
		"new" })
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
	public ModelAndView doPostview(Project project, HttpServletRequest request,
			HttpServletResponse response) {
		String message = null;
		Connection connection = null;
		try {
			connection = ds.getConnection();
			PreparedStatement ps = connection
					.prepareStatement("INSERT INTO project (name, description) values(?,?)");
			ps.setString(1, project.getName());
			ps.setString(2, project.getDescription());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			message = e.getMessage();
		} finally {
			closeConnection(connection);
		}
		return new ModelAndView("new", "message",
				message == null ? "Project Created" : message);
	}

	private void closeConnection(Connection connection) {

		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}
