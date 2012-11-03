package ahp.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Jama.Matrix;
import ahp.cache.CacheAccessor;
import ahp.model.AggregateResult;
import ahp.model.AhpModel;
import ahp.model.CriteriaType;
import ahp.model.PairwiseMatrix;
import ahp.model.PairwiseResult;
import ahp.model.User;
import ahp.model.helpers.DefaultModelGenerator;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rits.cloning.Cloner;

@org.springframework.stereotype.Controller
@org.springframework.web.bind.annotation.RequestMapping(value = { "/index",
		"new", "evaluate", "prepare" ,"setup","/", "/cache","/refresh","/list"})
public class Controller {
	/** The mapper. */
	private ObjectMapper mapper = new ObjectMapper(); 

	@RequestMapping(value = { "/index","/" }, method = RequestMethod.GET)
	public ModelAndView doGetIndex(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		CacheAccessor cache = new CacheAccessor();
		Object o = cache.getObjectFromCache("projects" ,CacheAccessor.ProjectReference);
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
		if ( ! map.containsKey(DefaultModelGenerator.GOAL_NAME_CAR)){
			AhpModel carModel = DefaultModelGenerator.generateCarModel();
			map.put(carModel.getGoalName(), carModel);
		}
		
		
		cache.putObjectInCacheIfNull("projects", map,CacheAccessor.ProjectReference);
		
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
			}else if ( tokens[0].equals("checkConsistency")){
				project.setCheckConsistency(Boolean.valueOf(tokens[1]));
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
		CacheAccessor cache = new CacheAccessor();
		Object o = cache.getObjectFromCache("projects",CacheAccessor.ProjectReference);
		Map<String,AhpModel> map;
		if ( o != null ){
			map = (Map<String,AhpModel>) o;
		}else{
			map = new HashMap<String,AhpModel>();
			cache.putObjectInCache("projects", map, CacheAccessor.ProjectReference);
			//TODO set up the default project here
		}
		map.put(project.getGoalName() , project);
		
		return new ModelAndView("jsonResult", "result", "{success:true}");
	}
	
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView doGetview(HttpSession session, String projectName, HttpServletRequest request,
			HttpServletResponse response) {
		CacheAccessor cache = new CacheAccessor();
		Map<String,AhpModel> map = (Map<String,AhpModel>) cache.getObjectFromCache("projects",CacheAccessor.ProjectReference);
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
		PairwiseMatrix criteria = project.getCriteria();

		for ( String r : results ){
			 PairwiseResult rs = new PairwiseResult();
			 rs.setResult(r);
			 if ( rs.isValid()){
				 criteria.setPairwiseByLabel(rs.getWinner(), rs.getLoser(), rs.getScore());
			 }else{
				 System.out.println("Invalid result not included");
			 }
		}
		
		if (project.isCheckConsistency() && !criteria.isConsistient()){
			return new ModelAndView("jsonResult", "result","//error\n" + "Criteria are not consistient");
		}
		project.setStatus("prepare");
		return new ModelAndView("jsonResult", "result","//success\n");
		
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView doCacheList( HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		CacheAccessor cache = new CacheAccessor();
		
		return new ModelAndView("cache", "aggregates", cache.getAllObjectsInCache(CacheAccessor.Aggregates));
		
	}
	
	@RequestMapping(value = "/cache", method = RequestMethod.GET)
	public ModelAndView doCacheAction( @RequestParam String aggregate, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		CacheAccessor cache = new CacheAccessor();
		if ( aggregate.equalsIgnoreCase("all")){
			cache.clear();
		}else{
			cache.destroy(aggregate,CacheAccessor.Aggregates);
		}
		return new ModelAndView("cache", "aggregates", cache.getAllObjectsInCache(CacheAccessor.Aggregates));
		
	}
	
	@RequestMapping(value = "/evaluate", method = RequestMethod.POST)
	public ModelAndView doPostEvaluate(@RequestParam String[] results,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		Map<String, PairwiseMatrix> pwMap = new HashMap<String, PairwiseMatrix>();
		Map<String, Matrix> realMap = new HashMap<String, Matrix>();
		AhpModel project = (AhpModel) session.getAttribute("selectedProject");
		
		project.setPwAlternatives(new HashMap<String, PairwiseMatrix>());
		project.setAlternatives(new HashMap<String, Matrix>());
		PairwiseResult[] bean = new PairwiseResult[results.length];
		int c = 0;
		for ( String r : results ){
			 PairwiseResult rs = new PairwiseResult();
			 rs.setResult(r);
			 if ( rs.isValid()){
				 bean[c++] = rs;
			 }else{
				 System.out.println("Invalid result not included");
				 bean = (PairwiseResult[]) ArrayUtils.remove(bean, bean.length-1);
			 }
		}
		
		String[] altLabels = project.getAlternativeLabels();
		for (PairwiseResult result : bean) {
			String type = result.getType();
			int denom = type.equals( CriteriaType.REAL_HIGHER_IS_BETTER ) ? 0 : 
				type.equals( CriteriaType.REAL_LOWER_IS_BETTER ) ? 1 : -1;
			if ( denom > -1 ){
				Matrix m;
				if (realMap.containsKey(result.getMatrixLabel())) {
					m = realMap.get(result.getMatrixLabel());
				}else{
					m = new Matrix(altLabels.length, 1);
					realMap.put(result.getMatrixLabel() , m);
				}
				double score = denom > 0 ? denom/result.getScore() : result.getScore();
				m.set(Arrays.binarySearch(altLabels, result.getWinner() ), 0, score);
			}else{
				if (!pwMap.containsKey(result.getMatrixLabel())) {
					pwMap.put(result.getMatrixLabel(),
							new PairwiseMatrix(project.getAlternativeLabels()));
				}
				pwMap.get(result.getMatrixLabel()).setPairwiseByLabel(
						result.getWinner(), result.getLoser(), result.getScore());
				}
		}
		
		boolean isConsistient = true;
		String errorMessage = "";
		for (String key : pwMap.keySet() ){
			project.addToPw(key, pwMap.get(key));
			if ( project.isCheckConsistency() && !pwMap.get(key).isConsistient() ){
				isConsistient = false;
				errorMessage += key + " is " + (pwMap.get(key).isConsistient() ? "consistient" : "inconsistient")+"\n";
			}
		}
		for (String key : realMap.keySet() ){
			project.addToAl(key, realMap.get(key));//normalised
		}
		if ( !isConsistient){
			return new ModelAndView("jsonResult", "result","//error\n" + errorMessage);
		}
		CacheAccessor cache = new CacheAccessor();
		Object o = cache.getObjectFromCache(project.getGoalName(),CacheAccessor.Aggregates);
		User u = new User(session.getId(), 1 );
		AggregateResult ag;
		if ( o == null ){
			ag = new AggregateResult(project.getGoalName());
			cache.putObjectInCache(project.getGoalName(), ag, CacheAccessor.Aggregates);
		}else{
			ag = (AggregateResult) o;
		}
		ag.addResult(project.getResult(), u );
		project.setStatus("complete");
		return new ModelAndView("jsonResult", "result","//success\n"+mapper.writeValueAsString( ag.getAggreateResult()));
	}
	
	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public ModelAndView doRefreshEvaluate(@RequestParam String projectName) throws JsonGenerationException, JsonMappingException, IOException{
		CacheAccessor cache = new CacheAccessor();
		Object o = cache.getObjectFromCache(projectName,CacheAccessor.Aggregates);
		AggregateResult ag = (AggregateResult) o;
		return new ModelAndView("jsonResult", "result","//success\n"+mapper.writeValueAsString( ag.getAggreateResult()));
	}

}
