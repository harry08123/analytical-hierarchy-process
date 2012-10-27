package ahp.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Jama.Matrix;
import ahp.model.AhpModel;
import ahp.model.CriteriaType;
import ahp.model.PairwiseMatrix;
import ahp.model.PairwiseResult;
import ahp.model.helpers.DefaultModelGenerator;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rits.cloning.Cloner;

@org.springframework.stereotype.Controller
@org.springframework.web.bind.annotation.RequestMapping(value = { "/index",
		"new", "evaluate", "prepare" ,"setup"})
public class Controller {
	private static Context envCtx;
	private static DataSource ds;
	/** The mapper. */
	private ObjectMapper mapper = new ObjectMapper();
	/*static {
		try {
			envCtx = (Context) new InitialContext().lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("jdbc/ahp");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public ModelAndView doGetIndex(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		Object o = session.getAttribute("projects");
		Map<String,AhpModel> map;
		if ( o != null ){
			map = (Map<String,AhpModel>) o;
		}else{
			map = new HashMap<String,AhpModel>();
			
		}
		if ( ! map.containsKey(DefaultModelGenerator.GOAL_NAME)){
			AhpModel defaultModel = DefaultModelGenerator.generateDefaultModel();
			map.put(defaultModel.getGoalName(), defaultModel);
		}
		session.setAttribute("projects", map);
		return new ModelAndView("index", "projects", map );
	}

	
	@RequestMapping(value = { "setup" }, method = RequestMethod.GET)
	public ModelAndView doGetSetup(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("setup");
	}
	
	@RequestMapping(value = { "setup" }, method = RequestMethod.POST)
	public ModelAndView doPostSetup(HttpSession session, String[] results, AhpModel project, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,String> cMap = new HashMap<String,String>();
		List<String> aList = new ArrayList<String>();
		for ( String s : results ){
			String[] tokens = s.split(":");
			if ( tokens[0].equals("criteria")){
				cMap.put(tokens[1], tokens[2]);
			}else if ( tokens[0].equals("alternative") ){
				aList.add(tokens[1]);
			}else if ( tokens[0].equals("goalName")){
				project.setGoalName(tokens[1]);
			}else if ( tokens[0].equals("goalDescription")){
				project.setGoalDescription(tokens[1]);
			}
		}
		
		project.setCriteriaType(cMap);
		String[] criteriaLabels = (String[])cMap.keySet().toArray(new String[cMap.keySet().size()]);
		Arrays.sort(criteriaLabels);
		project.setCriteriaLabels(criteriaLabels);
		project.setCriteria(new PairwiseMatrix(project.getCriteriaLabels()));
		
		String[] alternativeLabels = (String[])aList.toArray(new String[aList.size()]);
		Arrays.sort(alternativeLabels);
		project.setAlternativeLabels(alternativeLabels);
		
		project.setStatus("prepare");
		Object o = session.getAttribute("projects");
		Map<String,AhpModel> map;
		if ( o != null ){
			map = (Map<String,AhpModel>) o;
		}else{
			map = new HashMap<String,AhpModel>();
			session.setAttribute("projects", map);
			//TODO set up the default project here
		}
		map.put(project.getGoalName() , project);
		
		return new ModelAndView("jsonResult", "result", "{success:true}");
	}
	
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView doGetview(HttpSession session, String projectName, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,AhpModel> map = (Map<String,AhpModel>) session.getAttribute("projects");
		AhpModel project = map.get(projectName);
		Cloner cloner=new Cloner();

		AhpModel clone=cloner.deepClone(project);
		// clone is a deep-clone of o
		session.setAttribute("selectedProject", clone);
		return new ModelAndView("new","model", clone);
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ModelAndView doPostview(HttpSession session, AhpModel project,
			HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("new");

	}

	@RequestMapping(value = "/prepare", method = RequestMethod.GET)
	public ModelAndView doPostReady( HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		AhpModel project = (AhpModel) session.getAttribute("selectedProject");
		project.setStatus("ready");	
		return new ModelAndView("new", "model", project);
		
	}
	
	@RequestMapping(value = "/prepare", method = RequestMethod.POST)
	public ModelAndView doPostPrepare(@RequestParam String[] results, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		AhpModel project = (AhpModel) session.getAttribute("selectedProject");
		project.setStatus("prepare");	
		return new ModelAndView("new", "model", project);
		
	}
	
	@RequestMapping(value = "/evaluate", method = RequestMethod.POST)
	public ModelAndView doPostEvaluate(@RequestParam String[] results,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		Map<String, PairwiseMatrix> pwMap = new HashMap<String, PairwiseMatrix>();
		Map<String, Matrix> realMap = new HashMap<String, Matrix>();
		AhpModel project = (AhpModel) session.getAttribute("selectedProject");
		PairwiseResult[] bean = new PairwiseResult[results.length];
		int c = 0;
		for ( String r : results ){
			 PairwiseResult rs = new PairwiseResult();
			 System.out.println(r);
			 rs.setResult(r);
			 System.out.println(rs.toString());
			 bean[c++] = rs;
		}
		
		String[] altLabels = project.getAlternativeLabels();
		for (PairwiseResult result : bean) {
			if (!result.isValid())continue;
			if ( result.getType().equals(CriteriaType.REAL_VALUE )){
				Matrix m;
				if (realMap.containsKey(result.getMatrixLabel())) {
					m = realMap.get(result.getMatrixLabel());
				}else{
					m = new Matrix(altLabels.length, 1);
					realMap.put(result.getMatrixLabel() , m);
				}
				m.set(Arrays.binarySearch(altLabels, result.getWinner() ), 0, result.getScore());
			}else{
				if (!pwMap.containsKey(result.getMatrixLabel())) {
					pwMap.put(result.getMatrixLabel(),
							new PairwiseMatrix(project.getAlternativeLabels()));
				}
				if ( result.isNoWinner() ) continue;
				pwMap.get(result.getMatrixLabel()).setPairwiseByLabel(
						result.getWinner(), result.getLoser(), result.getScore());
				}
		}
		for (String key : pwMap.keySet() ){
			project.addToPw(key, pwMap.get(key));
		}
		
		for (String key : realMap.keySet() ){
			project.addToAl(key, realMap.get(key));
		}
		project.setStatus("complete");
		return new ModelAndView("jsonResult", "result",mapper.writeValueAsString( project.getResult()));
	}

	
	/*
	 * String message = null; Connection connection = null; try { connection =
	 * ds.getConnection(); PreparedStatement ps = connection
	 * .prepareStatement("INSERT INTO project (name, description) values(?,?)");
	 * ps.setString(1, project.getGoalName()); ps.setString(2,
	 * project.getGoalDescription()); ps.execute(); } catch (SQLException e) {
	 * e.printStackTrace(); message = e.getMessage(); } finally {
	 * closeConnection(connection); } return new ModelAndView("new", "message",
	 * message == null ? "Project Created" : message);
	 * 
	 * private void closeConnection(Connection connection) {
	 * 
	 * if (connection != null) try { connection.close(); } catch (SQLException
	 * e) { e.printStackTrace(); } }
	 */

}
