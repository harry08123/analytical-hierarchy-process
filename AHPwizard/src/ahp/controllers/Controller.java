package ahp.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;





@org.springframework.stereotype.Controller
@org.springframework.web.bind.annotation.RequestMapping(
		value={"/index", "new"})
public class Controller {
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView doGetIndex(HttpServletRequest request , HttpServletResponse response ) {
		return new ModelAndView("index", "message", "a test" );
	}
	
	@RequestMapping(value="/new",method=RequestMethod.GET)
	public ModelAndView doGetview(HttpServletRequest request , HttpServletResponse response ) {
		return new ModelAndView("new", "message", "a test" );
	}
	
	//ImsHumanPersist bean, @RequestParam String dn
	@RequestMapping(value="/new",method=RequestMethod.POST)
	public ModelAndView doPostview(  HttpServletRequest request , HttpServletResponse response ) {
		return new ModelAndView("new", "message", "a test" );
	}


}
